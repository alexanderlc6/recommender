/*
package com.sp.cf.controller;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

*/
/**
 * alexlu 2018.2.22
 *//*

@RestController
@RequestMapping("/recommander/elasticsearch")
public class ESController {
   @Autowired
    private TransportClient client;
    */
/*@GetMapping("/")
    public String index(){return "index";}*//*


    //单一查询
    @GetMapping("/get")
    @ResponseBody
    public ResponseEntity get(@RequestParam(name = "id",defaultValue = "") String id){
        if(id.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        GetResponse result = this.client.prepareGet("book", "novel", id).get();

        if(!result.isExists()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(result.getSource(), HttpStatus.OK);
    }

    //新增
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity add(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "author") String author,
            @RequestParam(name = "word_count") int wordCount,
            @RequestParam(name = "publish_date")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                    Date publishDate){
        try {
            XContentBuilder content = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("title", title)
                    .field("author", author)
                    .field("word_count", wordCount)
                    .field("publish_date", publishDate.getTime())
                    .endObject();

            IndexResponse result = this.client.prepareIndex("book", "novel")
                    .setSource(content)
                    .get();
            return new ResponseEntity(result.getId(),HttpStatus.OK);
        }catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    //删除
    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity delete(@RequestParam(name = "id") String id){
        DeleteResponse result = this.client.prepareDelete("book", "novel", id).get();
        return new ResponseEntity(HttpStatus.OK);
    }

    //修改
    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity update(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "author", required = false) String author
    ){
        UpdateRequest update = new UpdateRequest("book", "novel", id);

        try {
            XContentBuilder builder= XContentFactory.jsonBuilder().startObject();
            if (title != null){
                builder.field("title", title);
            }
            if(author != null){
                builder.field("author", author);
            }
            builder.endObject();
            update.doc(builder);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            UpdateResponse result = this.client.update(update).get();
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //复合查询
    @PostMapping("/query")
    @ResponseBody
    public ResponseEntity query(
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "gt_word_count", defaultValue = "0") int gtWordCount,
            @RequestParam(name = "lt_word_count", required = false) Integer ltWordCount
    ){
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if(author != null){
            boolQuery.must(QueryBuilders.matchQuery("author", author));
        }

        if(title != null){
            boolQuery.must(QueryBuilders.matchQuery("title", title));
        }

        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("word_count").from(gtWordCount);

        if(ltWordCount != null && ltWordCount > 0){
            rangeQuery.to(ltWordCount);
        }

        boolQuery.filter(rangeQuery);

        SearchRequestBuilder builder = this.client.prepareSearch("book")
                .setTypes("novel")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQuery)
                .setFrom(0)
                .setSize(10);

        //log.info(String.valueOf(builder));

        SearchResponse response = builder.get();
        List<Map<String, Object>> result = new ArrayList<>();

        for (SearchHit hit : response.getHits()){
            result.add(hit.sourceAsMap());
        }
        return new ResponseEntity(result, HttpStatus.OK);

    }

    //全文检索
    @PostMapping("/termQuery")
    @ResponseBody
    public ResponseEntity termQuery(
            @RequestParam(name = "keyword", required = true) String keyword,
            @RequestParam(name = "value", required = true) String value,
            @RequestParam(name = "gt_word_count", defaultValue = "0") int gtWordCount,
            @RequestParam(name = "lt_word_count", required = false) Integer ltWordCount
    ){
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if(keyword != null){
            boolQuery.must(QueryBuilders.matchQuery("keyword", keyword));
        }

        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("word_count").from(gtWordCount);

        if(ltWordCount != null && ltWordCount > 0){
            rangeQuery.to(ltWordCount);
        }

        boolQuery.filter(rangeQuery);

        QueryBuilder queryBuilder = QueryBuilders.termQuery(keyword,value);
        SearchResponse searchResponse = client.prepareSearch("index")
                .setTypes("fulltext")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(queryBuilder)
                .execute()
                .actionGet();


        List<Map<String, Object>> result = new ArrayList<>();

        for (SearchHit hit : searchResponse.getHits()){
            result.add(hit.sourceAsMap());
        }
        return new ResponseEntity(result, HttpStatus.OK);

    }
}
*/
