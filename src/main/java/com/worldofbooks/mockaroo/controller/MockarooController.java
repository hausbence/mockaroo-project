package com.worldofbooks.mockaroo.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.worldofbooks.mockaroo.service.ListingProvider;
import com.worldofbooks.mockaroo.service.ListingStatusProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MockarooController {

    @Autowired
    ListingProvider listingProvider;

    @Autowired
    ListingStatusProvider listingStatusProvider;

    @GetMapping("/listing")
    private String getListingObjects() throws UnirestException {
        return listingProvider.getAllListingObjects();
    }

    @GetMapping("/location")
    private String getLocationObjects() throws UnirestException {
        return listingStatusProvider.getAllListingStatusObjects();
    }
}
