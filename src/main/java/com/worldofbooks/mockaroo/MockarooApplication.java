package com.worldofbooks.mockaroo;

import com.worldofbooks.mockaroo.json.JSONWriter;
import com.worldofbooks.mockaroo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MockarooApplication {

    @Autowired
    ListingProvider listingProvider;

    @Autowired
    ListingStatusProvider listingStatusProvider;

    @Autowired
    LocationProvider locationProvider;

    @Autowired
    MarketPlaceProvider marketPlaceProvider;

    @Autowired
    JSONWriter jsonWriter;

    public static void main(String[] args) {
        SpringApplication.run(MockarooApplication.class, args);
    }

    @Bean
    void init() throws Exception {
        marketPlaceProvider.getMarketPlaceObjectsJSONArray();
        listingStatusProvider.getListingStatusObjectsJSONArray();
        locationProvider.getLocationObjectsJSONArray();
        listingProvider.getAllListingObjectsJSONArray();
        jsonWriter.createJSONFile();
    }

}
