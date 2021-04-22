package com.worldofbooks.mockaroo.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.worldofbooks.mockaroo.entity.Location;
import com.worldofbooks.mockaroo.repository.LocationRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LocationProvider {

    @Value("${mockaroo.url}")
    private String mockarooBaseUrl;

    @Value("${mockaroo.key}")
    private String apiKey;

    @Autowired
    LocationRepository locationRepository;

    public JSONArray getLocationObjectsJSONArray() throws UnirestException {
        String url = mockarooBaseUrl + "location?key=" + apiKey;
        HttpResponse<JsonNode> response = Unirest.get(url)
            .asJson();

        JSONArray arrayOfLocationObjects = response.getBody().getArray();
        saveLocationObjects(arrayOfLocationObjects);

        return arrayOfLocationObjects;
    }

    private void saveLocationObjects(JSONArray arrayOfLocationObjects) {
        for (Object object : arrayOfLocationObjects) {
            JSONObject jsonObject = (JSONObject) object;
            Location location = Location.builder()
                .id(UUID.fromString((String) jsonObject.get("id")))
                .manager_name(jsonObject.getString("manager_name"))
                .phone(jsonObject.getString("phone"))
                .address_primary(jsonObject.getString("address_primary"))
                .address_secondary(String.valueOf(jsonObject.get("address_secondary")))
                .country(jsonObject.getString("country"))
                .town(jsonObject.getString("town"))
                .postal_code(String.valueOf(jsonObject.get("postal_code")))
                .build();

            locationRepository.save(location);
        }
    }

}
