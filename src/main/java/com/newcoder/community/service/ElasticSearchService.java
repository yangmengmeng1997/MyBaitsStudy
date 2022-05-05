package com.newcoder.community.service;

import com.newcoder.community.dao.elasticSearch.DiscussPostRepository;
import com.newcoder.community.entity.DiscussPost;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiuxiaoran
 * @date 2022/5/4 21:56
 */
@Service
public class ElasticSearchService {
    @Autowired
    private DiscussPostRepository discussPostRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    //增加评论
    public void saveDiscussPost(DiscussPost discussPost){
        discussPostRepository.save(discussPost);
    }

    //删除评论信息
    public void deleteDiscussPost(Integer id){
        discussPostRepository.deleteById(id);
    }

    //搜索方法,关键方法，重要
    public Page<DiscussPost> searchDiscussPost(String keyWords,int current,int limit){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyWords,"title","content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(current,limit))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();

        Page<DiscussPost> page = elasticsearchTemplate.queryForPage(searchQuery, DiscussPost.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                //处理得到的数据
                SearchHits hits = searchResponse.getHits();
                if(hits.getTotalHits()<=0){
                    return null;
                }
                List<DiscussPost> list = new ArrayList<>();
                for(SearchHit searchHit:hits){
                    DiscussPost post = new DiscussPost();
                    String id = searchHit.getSourceAsMap().get("id").toString();
                    post.setId(Integer.parseInt(id));
                    String userId = searchHit.getSourceAsMap().get("userId").toString();
                    post.setUserId(Integer.parseInt(userId));
                    String title = searchHit.getSourceAsMap().get("title").toString();
                    post.setTitle(title);
                    String content = searchHit.getSourceAsMap().get("content").toString();
                    post.setContent(content);
                    String type = searchHit.getSourceAsMap().get("type").toString();
                    post.setType(Integer.parseInt(type));
                    String status = searchHit.getSourceAsMap().get("status").toString();
                    post.setStatus(Integer.parseInt(status));
                    String createTime = searchHit.getSourceAsMap().get("createTime").toString();
                    post.setCreateTime(new Date(Long.parseLong(createTime)));
                    String commentCount = searchHit.getSourceAsMap().get("commentCount").toString();
                    post.setCommentCount(Integer.parseInt(commentCount));

                    //处理高亮显示的内容
                    HighlightField titleFiled = searchHit.getHighlightFields().get("title");
                    if(titleFiled!=null){
                        post.setTitle(titleFiled.getFragments()[0].toString());  //多出高亮取第一段
                    }

                    HighlightField ContentFiled = searchHit.getHighlightFields().get("content");
                    if(ContentFiled!=null){
                        post.setContent(ContentFiled.getFragments()[0].toString());  //多出高亮取第一段
                    }
                    list.add(post);
                }
                return new AggregatedPageImpl(list,pageable,
                        hits.getTotalHits(),searchResponse.getAggregations(),searchResponse.getScrollId(),hits.getMaxScore());
            }

            @Override
            public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                return null;
            }
        });
        return page;
    }

}
