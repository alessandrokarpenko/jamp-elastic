package com.jamp.elastic.api.high;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jamp.elastic.model.AllSearchResponse;
import com.jamp.elastic.model.Event;
import com.jamp.elastic.model.EventType;
import org.apache.http.HttpHost;
import org.apache.http.util.Asserts;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HighLevelRestApiClient {

    RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("localhost", 9200, "http")));

    public void createIndex(String name) {
        CreateIndexRequest request = new CreateIndexRequest(name);
        try {
            CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
            System.out.println("Created: " + response.isAcknowledged());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchAllEvents() {
        SearchRequest searchRequest = new SearchRequest("events");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            var gson = new Gson();
            List<JsonElement> jsonElements = Arrays.stream(searchResponse.getHits().getHits())
                    .map(x -> gson.toJsonTree(x.getSourceAsMap())).collect(Collectors.toList());
            List<Event> events = jsonElements.stream().map(x -> gson.fromJson(x, Event.class))
                    .collect(Collectors.toList());
            System.out.println("Found: " + events.size());
            events.forEach(x -> System.out.println(x));
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchEventsWithTitle(String title) {
        SearchRequest searchRequest = new SearchRequest("events");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", title).operator(Operator.AND));
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            var gson = new Gson();
            List<JsonElement> jsonElements = Arrays.stream(searchResponse.getHits().getHits())
                    .map(x -> gson.toJsonTree(x.getSourceAsMap())).collect(Collectors.toList());
            List<Event> events = jsonElements.stream().map(x -> gson.fromJson(x, Event.class))
                    .collect(Collectors.toList());
            System.out.println("Found: " + events.size());
            events.forEach(x -> System.out.println(x));
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchWorkshopEvents() {
        SearchRequest searchRequest = new SearchRequest("events");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("eventType", EventType.WORKSHOP));
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            var gson = new Gson();
            List<JsonElement> jsonElements = Arrays.stream(searchResponse.getHits().getHits())
                    .map(x -> gson.toJsonTree(x.getSourceAsMap())).collect(Collectors.toList());
            List<Event> events = jsonElements.stream().map(x -> gson.fromJson(x, Event.class))
                    .collect(Collectors.toList());
            System.out.println("Found: " + events.size());
            events.forEach(x -> System.out.println(x));
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchRangeAndMatch() {
        SearchRequest searchRequest = new SearchRequest("events");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder rangeQuery = QueryBuilders.rangeQuery("date").gte("2021-04-04");
        QueryBuilder matchQuery = QueryBuilders.matchQuery("title", "Title 1").operator(Operator.OR);
        QueryBuilder qb = QueryBuilders
                .boolQuery()
                .must(rangeQuery)
                .must(matchQuery);
        searchSourceBuilder.query(qb);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            var gson = new Gson();
            List<JsonElement> jsonElements = Arrays.stream(searchResponse.getHits().getHits())
                    .map(x -> gson.toJsonTree(x.getSourceAsMap())).collect(Collectors.toList());
            List<Event> events = jsonElements.stream().map(x -> gson.fromJson(x, Event.class))
                    .collect(Collectors.toList());
            System.out.println("Found: " + events.size());
            events.forEach(x -> System.out.println(x));
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bulkDelete() {
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest("events", "kZ3VdXkBF-N17lPThDDT"));
        try {
            var response = client.bulk(request, RequestOptions.DEFAULT);
            System.out.println("hasFailures: " + response.hasFailures());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}