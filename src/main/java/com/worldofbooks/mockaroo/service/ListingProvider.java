package com.worldofbooks.mockaroo.service;

import com.google.gson.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.worldofbooks.mockaroo.entity.Listing;
import com.worldofbooks.mockaroo.entity.ListingStatus;
import com.worldofbooks.mockaroo.entity.Location;
import com.worldofbooks.mockaroo.entity.MarketPlace;
import com.worldofbooks.mockaroo.repository.ListingRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ListingProvider {

    @Value("${mockaroo.url}")
    private String mockarooBaseUrl;

    @Value("${mockaroo.key}")
    private String apiKey;

    @Autowired
    ListingRepository listingRepository;

    public String getAllListingObjects() throws UnirestException {
        String url = mockarooBaseUrl + "listing?key=" + apiKey;
        HttpResponse<JsonNode> response = Unirest.get(url)
            .asJson();

        JSONArray arrayOfListingObjects = response.getBody().getArray();

        //saveListingObjects(arrayOfListingObjects);

        return getJson(response);
    }

    /**
     * TODO
     * Building listingObject not working yet
     * Location, MarketPlace, ListingStatus needs to fetched before
     */
//    public void saveListingObjects(JSONArray listingObjects) {
//        for (Object object : listingObjects) {
//            Location location = new Location();
//            MarketPlace marketPlace = new MarketPlace();
//            ListingStatus listingStatus = new ListingStatus();
//            JSONObject jsonObject = (JSONObject) object;
//            Listing listingObject = Listing.builder()
//                .id(UUID.fromString((String) jsonObject.get("id")))
//                .upload_time((Date) jsonObject.get("upload_time"))
//                .quantity(jsonObject.getDouble("quantity"))
//                .marketPlace((MarketPlace) jsonObject.get("marketplace"))
//                .listingStatus((ListingStatus) jsonObject.get("listing_status"))
//                .description(jsonObject.getString("description"))
//                .listing_price(jsonObject.getDouble("listing_price"))
//                .currency(jsonObject.getString("currency"))
//                .owner_email_address(jsonObject.getString("owner_email_address"))
//                .title(jsonObject.getString("title"))
//                .location((Location) jsonObject.get("location"))
//                .build();
//
//            listingRepository.save(listingObject);
//
//        }
//
//    }

    private String getJson(HttpResponse<JsonNode> response) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());

        return gson.toJson(je);
    }
}
