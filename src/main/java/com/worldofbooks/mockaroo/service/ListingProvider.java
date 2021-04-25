package com.worldofbooks.mockaroo.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.worldofbooks.mockaroo.csv.CSVWriter;
import com.worldofbooks.mockaroo.entity.Listing;
import com.worldofbooks.mockaroo.repository.ListingRepository;
import com.worldofbooks.mockaroo.repository.ListingStatusRepository;
import com.worldofbooks.mockaroo.repository.LocationRepository;
import com.worldofbooks.mockaroo.repository.MarketPlaceRepository;
import com.worldofbooks.mockaroo.validation.DataValidator;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingProvider {

    @Value("${mockaroo.url}")
    private String mockarooBaseUrl;

    @Value("${mockaroo.key}")
    private String apiKey;

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    DataValidator dataValidator;

    @Autowired
    CSVWriter csvWriter;

    public JSONArray getAllListingObjectsJSONArray() throws Exception {
        String url = mockarooBaseUrl + "listing?key=" + apiKey;
        HttpResponse<JsonNode> response = Unirest.get(url)
            .asJson();

        JSONArray arrayOfListingObjects = response.getBody().getArray();

        List<Listing> validListingObjects = dataValidator.getListWithValidElements(arrayOfListingObjects);
        saveValidatedListingObjects(validListingObjects);
        csvWriter.createCSVFile(dataValidator.getInvalidListingObjects());

        return arrayOfListingObjects;
    }

    public void saveValidatedListingObjects(List<Listing> validListingObjects) throws Exception {
        for (Listing listingObject : validListingObjects) {
            listingRepository.save(listingObject);
        }
    }
}
