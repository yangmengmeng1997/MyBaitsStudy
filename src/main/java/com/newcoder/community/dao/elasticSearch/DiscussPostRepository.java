package com.newcoder.community.dao.elasticSearch;

import com.newcoder.community.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xiuxiaoran
 * @date 2022/5/3 22:43
 * 数据库访问层
 * es操作数据库的接口，之后直接调用即可
 */
@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost,Integer> {

}
