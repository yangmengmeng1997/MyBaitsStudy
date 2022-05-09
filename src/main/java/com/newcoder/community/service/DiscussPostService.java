package com.newcoder.community.service;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.newcoder.community.dao.DiscussPostMapper;
import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.util.CommunityUtil;
import com.newcoder.community.util.SensitiveFilter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xiuxiaoran
 * @date 2022/4/20 22:30
 * Caffeine进行缓存成功了，但是我们这边没有测试，看了视屏了解一个大概就OK了，速度确实有提升
 */

@Service
public class DiscussPostService {

    private static final Logger logger = LoggerFactory.getLogger(DiscussPostService.class);

    @Autowired(required = false)
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Value("${caffeine.posts.max-size}")
    private int maxSize;

    @Value("${caffeine.posts.expire-seconds}")
    private int expiredSeconds;

    /*
       Caffeine 进行缓存
     */
    //帖子缓存
    private LoadingCache<String,List<DiscussPost>> postListCache;

    //帖子总数缓存
    private LoadingCache<Integer,Integer> postRowsCache;

    @PostConstruct
    public void init(){
        //初始化缓存组件
        postListCache = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expiredSeconds, TimeUnit.SECONDS)
                .build(new CacheLoader<String, List<DiscussPost>>() {
                    @Nullable
                    @Override
                    public List<DiscussPost> load(@NonNull String key) throws Exception {
                        //实现从数据库中写入数据到缓存中进行查询
                        if(key==null || key.length()==0) throw new IllegalArgumentException("参数错误");
                        String[] params = key.split(":");
                        if(params==null || params.length!=2)  throw new IllegalArgumentException("参数错误");
                        int offset = Integer.valueOf(params[0]);
                        int limit = Integer.valueOf(params[1]);
                        //只适用于查首页并且按照热度

                        //可以设计为先访问redis，再访问数据库，但是这里没有写，直接到数据库
                        logger.debug("Load data from DB");
                        return discussPostMapper.selectDiscussPosts(0,offset,limit,1);
                    }
                });

        //初始化缓存计算帖子总数
        postRowsCache = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expiredSeconds, TimeUnit.SECONDS)
                .build(new CacheLoader<Integer, Integer>() {
                    @Nullable
                    @Override
                    public Integer load(@NonNull Integer key) throws Exception {
                        logger.debug("Load data from DB");
                        return discussPostMapper.selectDiscussPostRows(key);
                    }
                });
    }


    public List<DiscussPost> findDiscussPost(int userId, int offset , int limit,int orderMode){
        if(userId==0 && orderMode==1){
            //当前仅当首页数据时并且是按照热度排行的帖子时，进行缓存
            return postListCache.get(offset+":"+limit);  //这里从缓存中获取
        }
        logger.debug("Load data from DB");
        return discussPostMapper.selectDiscussPosts(userId,offset,limit,orderMode);
    }

    public int findDiscussPostRows(int userId){
        if(userId==0){
            //首页查询
            return postRowsCache.get(userId);
        }
        logger.debug("Load data from DB");
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    public int addDiscussPost(DiscussPost post){
        if(post==null){
            throw new IllegalArgumentException("参数不为空");
        }
        //转义HTML需要先过滤掉文本里面的特殊字符使用工具箱过滤
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        //过滤敏感词
        post.setTitle(sensitiveFilter.filter(post.getTitle()));
        post.setContent(sensitiveFilter.filter(post.getContent()));
        return discussPostMapper.insertDiscussPost(post);
    }

    public DiscussPost findDiscussPostById(int id){
        return discussPostMapper.selectDiscussPostById(id);
    }

    //更新帖子数量
    public int updateCommentCount(int id,int commentCount){
        return discussPostMapper.updateCommentCount(id,commentCount);
    }

    //更新帖子状态和类型
    public int updateType(int id,int type){
        return discussPostMapper.updateType(id,type);
    }

    public int updateStatus(int id,int status){
        return discussPostMapper.updateStatus(id,status);
    }

    public int updateScore(int id,double score){
        return discussPostMapper.updateScore(id,score);
    }


}
