package com.worldofbooks.mockaroo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
public class InvalidListingObject {

    private UUID listingId;

    private String marketPlaceName;

    private String invalidFieldName;

}
