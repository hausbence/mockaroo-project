package com.worldofbooks.mockaroo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ListingStatusProvider {

    @Value("${mockaroo.url}")
    private String mockarooBaseUrl;

    @Value("${mockaroo.key}")
    private String apiKey;

}
