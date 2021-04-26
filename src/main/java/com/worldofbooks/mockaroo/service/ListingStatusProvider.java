package com.worldofbooks.mockaroo.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.worldofbooks.mockaroo.entity.ListingStatus;
import com.worldofbooks.mockaroo.repository.ListingStatusRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ListingStatusProvider {

    @Value("${mockaroo.url}")
    private String mockarooBaseUrl;

    @Value("${mockaroo.key}")
    private String apiKey;

    @Autowired
    ListingStatusRepository listingStatusRepository;

    public void fetchAndHandleListingStatusObjects() throws UnirestException {
        String url = mockarooBaseUrl + "listingStatus?key=" + apiKey;
        HttpResponse<JsonNode> response = Unirest.get(url)
            .asJson();

        JSONArray arrayOfListingStatusObjects = response.getBody().getArray();

        saveListingStatusObjects(arrayOfListingStatusObjects);

    }

    private void saveListingStatusObjects(JSONArray arrayOfListingStatusObjects) {
        for(Object object : arrayOfListingStatusObjects) {
            JSONObject jsonObject = (JSONObject) object;
            ListingStatus listingStatus = ListingStatus.builder()
                .id(jsonObject.getLong("id"))
                .status_name(jsonObject.getString("status_name"))
                .build();

            listingStatusRepository.save(listingStatus);
        }

    }

}
