package com.worldofbooks.mockaroo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class Report {

    private long totalListingCount;

    private int totalEbayListingCount;

    private double totalEbayListingPrice;

    private double avgEbayListingPrice;

    private double totalAmazonListingCount;

    private double totalAmazonListingPrice;

    private double avgAmazonListingPrice;

    private String bestListerEmailAddress;

    private List<Object> monthlyEbayReports;

    private List<Object> monthlyAmazonReports;

}
