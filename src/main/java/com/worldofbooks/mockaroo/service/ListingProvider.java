package com.worldofbooks.mockaroo.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.worldofbooks.mockaroo.entity.Listing;
import com.worldofbooks.mockaroo.repository.ListingRepository;
import com.worldofbooks.mockaroo.repository.ListingStatusRepository;
import com.worldofbooks.mockaroo.repository.LocationRepository;
import com.worldofbooks.mockaroo.repository.MarketPlaceRepository;
import com.worldofbooks.mockaroo.validation.DataValidation;
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
    LocationRepository locationRepository;

    @Autowired
    ListingStatusRepository listingStatusRepository;

    @Autowired
    MarketPlaceRepository marketPlaceRepository;

    @Autowired
    DataValidation dataValidation;

    public JSONArray getAllListingObjectsJSONArray() throws Exception {
        String url = mockarooBaseUrl + "listing?key=" + apiKey;
        HttpResponse<JsonNode> response = Unirest.get(url)
            .asJson();

        JSONArray arrayOfListingObjects = response.getBody().getArray();

        List<Listing> validListingObjects = dataValidation.getListWithValidElements(arrayOfListingObjects);
        saveValidatedListingObjects(validListingObjects);

        return arrayOfListingObjects;
    }

    /**
     * TODO
     * Handling null values, and logging them to CSV.
     */
    public void saveValidatedListingObjects(List<Listing> validListingObjects) throws Exception {
        for (Listing listingObject : validListingObjects) {
            listingRepository.save(listingObject);
        }
    }
}
