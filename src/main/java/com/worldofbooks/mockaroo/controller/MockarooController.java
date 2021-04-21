package com.worldofbooks.mockaroo.controller;

import com.google.gson.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.worldofbooks.mockaroo.service.ListingProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

@RestController
public class MockarooController {

    @Autowired
    ListingProvider listingProvider;

    @GetMapping("/listing")
    private JSONArray getListingObjects() throws UnirestException {
        return listingProvider.getAllListingObjects();
    }

    private String getJson(HttpResponse<JsonNode> response) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());

        return gson.toJson(je);
    }
}
