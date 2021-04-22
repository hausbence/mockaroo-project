package com.worldofbooks.mockaroo;

import com.worldofbooks.mockaroo.controller.MockarooController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MockarooApplication {

    @Autowired
    MockarooController mockarooController;

    public static void main(String[] args) {
        SpringApplication.run(MockarooApplication.class, args);
    }

    @Bean
    void init() throws Exception {
        mockarooController.getMarketPlaceObjects();
        mockarooController.getListingStatusObjects();
        mockarooController.getLocationObjects();
        mockarooController.getListingObjects();
    }

}
