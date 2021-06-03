package com.jamp.elastic.api.low;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jamp.elastic.model.AllSearchResponse;
import com.jamp.elastic.model.Event;
import org.apache.http.HttpHost;
import org.apache.http.util.Asserts;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class LowLevelRestApiClient {

    RestClient restClient = RestClient.builder(
            new HttpHost("localhost", 9200, "http")).build();

    public void createIndex(String name) {
        Request request = new Request("PUT", String.format("/%s", name));
        try {
            Response response = restClient.performRequest(request);
            Asserts.check(response.getStatusLine().getStatusCode() == 200, "Wrong status code");
            System.out.println(EntityUtils.toString(response.getEntity()));
            restClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createEvent(Event event) {
        Request request = new Request("POST", "/events/event");
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd").create();
        request.setJsonEntity(gson.toJson(event));
        try {
            Response response = restClient.performRequest(request);
            Asserts.check(response.getStatusLine().getStatusCode() == 201, "Wrong status code");
            System.out.println(EntityUtils.toString(response.getEntity()));
            restClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void searchAllEvents() {
        Request request = new Request("POST", "/events/event/_search");
        String payload = "{\"query\": {\"match_all\": {}}}";
        request.setJsonEntity(payload);
        try {
            Response response = restClient.performRequest(request);
            Asserts.check(response.getStatusLine().getStatusCode() == 200, "Wrong status code");
            InputStream input = response.getEntity().getContent();
            Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
            AllSearchResponse result = new Gson().fromJson(reader, AllSearchResponse.class);
            System.out.println(EntityUtils.toString(response.getEntity()));
            result.getHits().getHits().forEach(x -> System.out.println(x.getEvent()));
            restClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchEventsWithTitle(String title) {
        Request request = new Request("POST", "/events/event/_search");
        String payload = String.format("{\"query\": {\"match\": {\"title\":\"%s\"}}}", title);
        request.setJsonEntity(payload);
        try {
            Response response = restClient.performRequest(request);
            Asserts.check(response.getStatusLine().getStatusCode() == 200, "Wrong status code");
            InputStream input = response.getEntity().getContent();
            Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
            AllSearchResponse result = new Gson().fromJson(reader, AllSearchResponse.class);
            System.out.println(EntityUtils.toString(response.getEntity()));
            result.getHits().getHits().forEach(x -> System.out.println(x.getEvent()));
            restClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchWorkshopEvents() {
        Request request = new Request("POST", "/events/event/_search");
        String payload = "{\"query\": {\"match\": {\"eventType\":\"WORKSHOP\"}}}";
        request.setJsonEntity(payload);
        try {
            Response response = restClient.performRequest(request);
            Asserts.check(response.getStatusLine().getStatusCode() == 200, "Wrong status code");
            InputStream input = response.getEntity().getContent();
            Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
            AllSearchResponse result = new Gson().fromJson(reader, AllSearchResponse.class);
            System.out.println(EntityUtils.toString(response.getEntity()));
            result.getHits().getHits().forEach(x -> System.out.println(x.getEvent()));
            restClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchRangeAndMatch() {
        Request request = new Request("POST", "/events/event/_search");
        String payload = "{\"query\":{\"bool\":{\"must\":[{\"range\":{\"date\":{\"gte\":\"2021-04-04\"}}},{\"match\":{\"title\":\"Title 1\"}}]}}}";
        request.setJsonEntity(payload);
        try {
            Response response = restClient.performRequest(request);
            Asserts.check(response.getStatusLine().getStatusCode() == 200, "Wrong status code");
            InputStream input = response.getEntity().getContent();
            Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
            AllSearchResponse result = new Gson().fromJson(reader, AllSearchResponse.class);
            System.out.println(EntityUtils.toString(response.getEntity()));
            result.getHits().getHits().forEach(x -> System.out.println(x.getEvent()));
            restClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}