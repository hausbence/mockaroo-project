package com.worldofbooks.mockaroo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class MonthlyData {

    private int totalEbayListingCount;

    private double totalEbayListingPrice;

    private double avgEbayListingPrice;

    private double avgAmazonListingPrice;

    private int totalAmazonListingCount;

    private double totalAmazonListingPrice;

    private String bestListerEmailAddress;
}
