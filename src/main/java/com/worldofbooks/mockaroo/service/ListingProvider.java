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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
            Location location = new Location();
            MarketPlace marketPlace = new MarketPlace();
            ListingStatus listingStatus = new ListingStatus();
            JSONObject listingJSONObject = (JSONObject) object;
            System.out.println(listingJSONObject);
            if (locationRepository.findById(UUID.fromString((String) listingJSONObject.get("location_id"))).isPresent()) {
                location = locationRepository.findById(UUID.fromString((String) listingJSONObject.get("location_id"))).get();
            } else {
                System.out.println("There is no location with this ID:  " + UUID.fromString((String) listingJSONObject.get("location_id")));
            }
            if (marketPlaceRepository.findById(listingJSONObject.getLong("marketplace")).isPresent()) {
                marketPlace  = marketPlaceRepository.findById(listingJSONObject.getLong("marketplace")).get();
            } else {
                System.out.println("There is no marketplace with this ID:   " + listingJSONObject.getLong("marketplace"));
            }
            if (listingStatusRepository.findById(listingJSONObject.getLong("listing_status")).isPresent()) {
                listingStatus = listingStatusRepository.findById(listingJSONObject.getLong("listing_status")).get();
            } else {
                System.out.println("There is no listing status with this ID: " + listingJSONObject.getLong("listing_status"));
            }

            DateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Listing listingObject = Listing.builder()
                .id(UUID.fromString((String) listingJSONObject.get("id")))
                .upload_time(simpleDateFormat.parse(String.valueOf(listingJSONObject.get("upload_time"))))
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
}
