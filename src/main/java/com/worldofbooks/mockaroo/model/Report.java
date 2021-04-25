package com.worldofbooks.mockaroo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class Report {

    private int totalListingCount;

    private int totalEbayListingCount;

    private double totalEbayListingPrice;

    private double avgEbayListingPrice;

    private int totalAmazonListingCount;

    private double totalAmazonListingPrice;

    private double avgAmazonListingPrice;

    private int bestListerEmailAddress;

    private List<MonthlyReport> monthlyReports;

}
