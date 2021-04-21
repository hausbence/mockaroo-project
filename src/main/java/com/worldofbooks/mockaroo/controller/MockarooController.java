package com.worldofbooks.mockaroo.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.worldofbooks.mockaroo.entity.Location;
import com.worldofbooks.mockaroo.service.ListingProvider;
import com.worldofbooks.mockaroo.service.ListingStatusProvider;
import com.worldofbooks.mockaroo.service.LocationProvider;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MockarooController {

    @Autowired
    ListingProvider listingProvider;

    @Autowired
    ListingStatusProvider listingStatusProvider;

    @Autowired
    LocationProvider locationProvider;

    @GetMapping("/listing")
    private String getListingObjects() throws UnirestException {
        return listingProvider.getAllListingObjects();
    }

    @GetMapping("/listingStatus")
    private JSONArray getListingStatusObjects() throws UnirestException {
        return listingStatusProvider.getListingStatusObjectsJSONArray();
    }

    @GetMapping("/location")
    private JSONArray getLocationObjects() throws UnirestException {
        return locationProvider.getLocationObjectsJSONArray();
    }
}
