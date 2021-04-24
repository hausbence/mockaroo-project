package com.worldofbooks.mockaroo.validation;

import com.worldofbooks.mockaroo.entity.Listing;
import com.worldofbooks.mockaroo.entity.ListingStatus;
import com.worldofbooks.mockaroo.entity.Location;
import com.worldofbooks.mockaroo.entity.MarketPlace;
import com.worldofbooks.mockaroo.model.InvalidListingObject;
import com.worldofbooks.mockaroo.repository.ListingStatusRepository;
import com.worldofbooks.mockaroo.repository.LocationRepository;
import com.worldofbooks.mockaroo.repository.MarketPlaceRepository;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DataValidator {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ListingStatusRepository listingStatusRepository;

    @Autowired
    MarketPlaceRepository marketPlaceRepository;

    @Getter
    private List<InvalidListingObject> invalidListingObjects = new ArrayList<>();


    public List<Listing> getListWithValidElements(JSONArray listingJSONArray) throws ParseException {
        List<Listing> listOfValidListingObjects = new ArrayList<>();

        DateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Location location;
        ListingStatus listingStatus;
        MarketPlace marketPlace;

        for (Object object : listingJSONArray) {
            JSONObject listingJSONObject = (JSONObject) object;
            if (isEveryStatementValid(listingJSONObject)) {
                location = locationRepository.findById(UUID.fromString((String) listingJSONObject.get("location_id"))).get();
                listingStatus = listingStatusRepository.findById(listingJSONObject.getLong("listing_status")).get();
                marketPlace = marketPlaceRepository.findById(listingJSONObject.getLong("marketplace")).get();

                Listing listingObject = Listing.builder()
                    .id(UUID.fromString((String) listingJSONObject.get("id")))
                    .upload_time(simpleDateFormat.parse((String) listingJSONObject.get("upload_time")))
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

    private boolean isEveryStatementValid(JSONObject listingJSONObject) {
        return
            notContainsNullElement(listingJSONObject) &&
                isValidLocationObject(listingJSONObject.getString("location_id")) &&
                isValidListingPrice(listingJSONObject.getDouble("listing_price")) &&
                isValidCurrency(listingJSONObject.getString("currency")) &&
                isValidQuantity(listingJSONObject.getInt("quantity")) &&
                isValidEmailAddress(listingJSONObject.getString("owner_email_address")) &&
                isValidListingStatusObject(listingJSONObject.getLong("listing_status")) &&
                isValidMarketplaceObject(listingJSONObject.getLong("marketplace"));
    }

    private boolean notContainsNullElement(JSONObject listingJSONObject) {
        Set<String> set = listingJSONObject.keySet();
        for (String key : set) {
            if (listingJSONObject.get(key) == null || listingJSONObject.isNull(key)) {
                addInvalidObject(listingJSONObject, key);
                return false;
            }
        }
        return true;
    }

    private void addInvalidObject(JSONObject listingJSONObject, String key) {
        InvalidListingObject invalidListingObject;
        Optional<MarketPlace> marketPlace = marketPlaceRepository.findById(listingJSONObject.getLong("marketplace"));
        Object listingId = listingJSONObject.get("id");
        if (marketPlace.isPresent() && listingId != null) {
            String marketPlaceName = marketPlace.get().getMarketplace_name();
            invalidListingObject = new InvalidListingObject(UUID.fromString((String) listingId), marketPlaceName, key);
        } else {
            invalidListingObject = marketPlace
                .map(place -> new InvalidListingObject(UUID.fromString("null"), place.getMarketplace_name(), key))
                .orElseGet(() -> new InvalidListingObject(UUID.fromString((String) listingId), "null", key));
        }
        invalidListingObjects.add(invalidListingObject);

    }

    public boolean isValidLocationObject(String locationId) {
        return locationId != null && locationRepository.findById(UUID.fromString(locationId)).isPresent();
    }

    public boolean isValidListingPrice(Double listing_price) {
        if (listing_price == null) {
            return false;
        }
        BigDecimal bigDecimalListingPrice = BigDecimal.valueOf(listing_price);

        return listing_price > 0 && bigDecimalListingPrice.scale() == 2;
    }

    public boolean isValidCurrency(String currency) {
        return currency != null && currency.length() == 3;
    }

    public boolean isValidQuantity(Integer quantity) {
        return quantity != null && quantity > 0;
    }

    public boolean isValidEmailAddress(String emailAddress) {
        String emailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return emailAddress != null && emailAddress.matches(emailRegex);
    }

    public boolean isValidListingStatusObject(Long statusId) {
        return statusId != null && listingStatusRepository.findById(statusId).isPresent();
    }

    public boolean isValidMarketplaceObject(Long marketPlaceId) {
        return marketPlaceId != null && marketPlaceRepository.findById(marketPlaceId).isPresent();
    }

}
