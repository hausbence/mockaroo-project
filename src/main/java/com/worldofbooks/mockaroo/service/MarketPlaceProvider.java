package com.worldofbooks.mockaroo.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.worldofbooks.mockaroo.entity.MarketPlace;
import com.worldofbooks.mockaroo.repository.MarketPlaceRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MarketPlaceProvider {

    @Value("${mockaroo.url}")
    private String mockarooBaseUrl;

    @Value("${mockaroo.key}")
    private String apiKey;

    @Autowired
    MarketPlaceRepository marketPlaceRepository;

    public JSONArray getMarketPlaceObjectsJSONArray() throws UnirestException {
        String url = mockarooBaseUrl + "marketplace?key=" + apiKey;
        HttpResponse<JsonNode> response = Unirest.get(url)
            .asJson();

        JSONArray arrayOfMarketPlaceObjects = response.getBody().getArray();
        saveMarketPlaceObjects(arrayOfMarketPlaceObjects);

        return arrayOfMarketPlaceObjects;
    }

    private void saveMarketPlaceObjects(JSONArray arrayOfMarketPlaceObjects) {

        for (Object object : arrayOfMarketPlaceObjects) {
            JSONObject jsonObject = (JSONObject) object;
            MarketPlace marketPlace = MarketPlace.builder()
                .id(jsonObject.getLong("id"))
                .marketplace_name(jsonObject.getString("marketplace_name"))
                .build();

            marketPlaceRepository.save(marketPlace);
        }

    }

}
