package com.newcoder.community.Quartz;

import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.service.DiscussPostService;
import com.newcoder.community.service.ElasticSearchService;
import com.newcoder.community.service.LikeService;
import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.RedisKeyUtil;
import org.aspectj.weaver.patterns.IScope;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiuxiaoran
 * @date 2022/5/7 20:44
 */
public class PostScoreRefreshJob implements Job , CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(PostScoreRefreshJob.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Autowired
    private static final Date epoch;  //牛客元年

    static {
        try {
            epoch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-08-01 00:00:00");
        } catch (ParseException e) {
            throw new RuntimeException("初始化牛客纪元失败", e);
        }
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String redisKey = RedisKeyUtil.getPostScoreKey();
        BoundSetOperations operations = redisTemplate.boundSetOps(redisKey);
        //判断有没有操作
        if (operations.size() == 0) {
            logger.info("任务取消，因为没有任何操作可以刷新");
            return;
        }
        logger.info("任务开始，正在刷新帖子分数" + operations.size());

        //操作
        while (operations.size() > 0) {  //表示有操作处理
            this.refresh((Integer) operations.pop());
        }

        logger.info("任务结束，刷新完毕");
    }

    private void refresh(int postId) {
        DiscussPost post = discussPostService.findDiscussPostById(postId);
        if (post == null) {
            logger.error("该帖子不存在，id是" + postId);
            return;
        }
        //开始算分
        //判断条件等
        boolean wonderful = post.getStatus() == 1;  // 1为加精
        int commentCount = post.getCommentCount(); //帖子总数
        long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, postId); //点赞数量

        //计算权重
        double w = (wonderful ? 75 : 0) + commentCount * 10 + likeCount * 2;
        //计算分数  ，分数不能为负，所以取>=1的
        double score = Math.log(Math.max(w, 1)) + (post.getCreateTime().getTime() - epoch.getTime()) / (3600 * 1000 * 24);

        //更新分数
        discussPostService.updateScore(postId,score);
        //同步搜索的数据
        post.setScore(score);
        elasticSearchService.saveDiscussPost(post);
    }
}
