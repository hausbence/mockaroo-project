package com.worldofbooks.mockaroo.validation;

import com.worldofbooks.mockaroo.entity.Listing;
import com.worldofbooks.mockaroo.entity.ListingStatus;
import com.worldofbooks.mockaroo.entity.Location;
import com.worldofbooks.mockaroo.entity.MarketPlace;
import com.worldofbooks.mockaroo.repository.ListingStatusRepository;
import com.worldofbooks.mockaroo.repository.LocationRepository;
import com.worldofbooks.mockaroo.repository.MarketPlaceRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class DataValidation {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ListingStatusRepository listingStatusRepository;

    @Autowired
    MarketPlaceRepository marketPlaceRepository;


    public List<Listing> getArrayWithValidElements(JSONArray listingJSONArray) throws ParseException {
        List<Listing> listOfValidListingObjects = new ArrayList<>();

        DateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Location location;
        ListingStatus listingStatus;
        MarketPlace marketPlace;

        for (Object object : listingJSONArray) {
            JSONObject listingJSONObject = (JSONObject) object;
            if (isValidTitle(listingJSONObject) &&
                isValidDescription(listingJSONObject) &&
                isValidLocationObject(listingJSONObject) &&
                isValidListingPrice(listingJSONObject) &&
                isValidCurrency(listingJSONObject) &&
                isValidQuantity(listingJSONObject) &&
                isValidEmailAddress(listingJSONObject) &&
                isValidListngStatusObject(listingJSONObject) &&
                isValidMarketplaceObject(listingJSONObject) &&
                isValidDate(listingJSONObject)) {
                location = locationRepository.findById(UUID.fromString((String) listingJSONObject.get("location_id"))).get();
                listingStatus = listingStatusRepository.findById(listingJSONObject.getLong("listing_status")).get();
                marketPlace = marketPlaceRepository.findById(listingJSONObject.getLong("marketplace")).get();

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

                listOfValidListingObjects.add(listingObject);
            }
        }
        return listOfValidListingObjects;
    }

    public List<Listing> getArrayWithInvalidElements(JSONArray listingJSONArray) {
        List<Listing> listOfInvalidListingObjects = new ArrayList<>();
        for (Object object : listingJSONArray) {
            JSONObject listingJSONObject = (JSONObject) object;
            if (!isValidTitle(listingJSONObject) ||
                !isValidDescription(listingJSONObject) ||
                !isValidLocationObject(listingJSONObject) ||
                !isValidListingPrice(listingJSONObject) ||
                !isValidCurrency(listingJSONObject) ||
                !isValidQuantity(listingJSONObject) ||
                !isValidEmailAddress(listingJSONObject) ||
                !isValidListngStatusObject(listingJSONObject) ||
                !isValidMarketplaceObject(listingJSONObject) ||
                !isValidDate(listingJSONObject)) {

                // If the object is invalid, I don't have to save it, I just need to log to CSV.
            }
        }
        return listOfInvalidListingObjects;
    }

    public boolean isValidTitle(JSONObject listingJSONObject) {
        return !String.valueOf(listingJSONObject.get("title")).equals("null");
    }

    public boolean isValidDescription(JSONObject listingJSONObject) {
        return !String.valueOf(listingJSONObject.get("description")).equals("null");
    }

    public boolean isValidLocationObject(JSONObject listingJSONObject) {
        return locationRepository.findById(UUID.fromString((String) listingJSONObject.get("location_id"))).isPresent();
    }

    public boolean isValidListingPrice(JSONObject listingJSONObject) {
        BigDecimal bigDecimalListingPrice = BigDecimal.valueOf(listingJSONObject.getDouble("listing_price"));

        if (listingJSONObject.getDouble("listing_price") < 0 ||
                listingJSONObject.getDouble("listing_price") == 0 ||
                    String.valueOf(listingJSONObject.get("listing_price")).equals("null") ||
                        bigDecimalListingPrice.scale() != 2) { return false; }

        else return true;
    }

    public boolean isValidCurrency(JSONObject listingJSONObject) {
        return !String.valueOf(listingJSONObject.get("currency")).equals("null") && listingJSONObject.getString("currency").length() != 3;
    }

    public boolean isValidQuantity(JSONObject listingJSONObject) {
        return !(listingJSONObject.getDouble("quantity") < 0) && listingJSONObject.get("quantity") != null;
    }

    public boolean isValidEmailAddress(JSONObject listingJSONObject) {
        String emailAddress = listingJSONObject.getString("owner_email_address");
        String emailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return emailAddress.matches(emailRegex);
    }

    public boolean isValidListngStatusObject(JSONObject listingJSONObject) {
        return listingStatusRepository.findById(listingJSONObject.getLong("listing_status")).isPresent();
    }

    public boolean isValidMarketplaceObject(JSONObject listingJSONObject) {
        return marketPlaceRepository.findById(listingJSONObject.getLong("marketplace")).isPresent();
    }

    public boolean isValidDate(JSONObject listingJSONObject) {
        return !String.valueOf(listingJSONObject.get("upload_time")).equals("null");
    }

}
