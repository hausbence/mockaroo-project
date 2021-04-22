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
import com.worldofbooks.mockaroo.repository.ListingStatusRepository;
import com.worldofbooks.mockaroo.repository.LocationRepository;
import com.worldofbooks.mockaroo.repository.MarketPlaceRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ListingProvider {

    @Value("${mockaroo.url}")
    private String mockarooBaseUrl;

    @Value("${mockaroo.key}")
    private String apiKey;

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ListingStatusRepository listingStatusRepository;

    @Autowired
    MarketPlaceRepository marketPlaceRepository;

    public JSONArray getAllListingObjects() throws Exception {
        String url = mockarooBaseUrl + "listing?key=" + apiKey;
        HttpResponse<JsonNode> response = Unirest.get(url)
            .asJson();

        JSONArray arrayOfListingObjects = response.getBody().getArray();

        saveListingObjects(arrayOfListingObjects);

        return arrayOfListingObjects;
    }

    /**
     * TODO
     * Handling null values, and logging them to CSV.
     */
    public void saveListingObjects(JSONArray listingObjects) throws Exception {
        for (Object object : listingObjects) {
            Location location;
            MarketPlace marketPlace;
            ListingStatus listingStatus;
            JSONObject listingJSONObject = (JSONObject) object;
            if (locationRepository.findById(UUID.fromString((String) listingJSONObject.get("location_id"))).isPresent()) {
                location = locationRepository.findById(UUID.fromString((String) listingJSONObject.get("location_id"))).get();
            } else {
                System.out.println("there is no location with this id --->   " + UUID.fromString((String) listingJSONObject.get("location_id")));
                continue;
            }
            if (marketPlaceRepository.findById(listingJSONObject.getLong("marketplace")).isPresent()) {
                marketPlace  = marketPlaceRepository.findById(listingJSONObject.getLong("marketplace")).get();
            } else {
                throw new Exception("There is no marketPlace with this ID");
            }
            if (listingStatusRepository.findById(listingJSONObject.getLong("listing_status")).isPresent()) {
                listingStatus = listingStatusRepository.findById(listingJSONObject.getLong("listing_status")).get();
            } else {
                throw new Exception("There is no listingStatus with this ID");
            }

            Listing listingObject = Listing.builder()
                .id(UUID.fromString((String) listingJSONObject.get("id")))
                .upload_time((Date) listingJSONObject.get("upload_time"))
                .quantity(listingJSONObject.getDouble("quantity"))
                .marketPlace(marketPlace)
                .listingStatus(listingStatus)
                .description(listingJSONObject.getString("description"))
                .listing_price(listingJSONObject.getDouble("listing_price"))
                .currency(listingJSONObject.getString("currency"))
                .owner_email_address(listingJSONObject.getString("owner_email_address"))
                .title(listingJSONObject.getString("title"))
                .location(location)
                .build();

            listingRepository.save(listingObject);

        }

    }

    private String getJson(HttpResponse<JsonNode> response) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());

        return gson.toJson(je);
    }
}
