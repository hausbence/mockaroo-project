package com.worldofbooks.mockaroo.service;

import com.worldofbooks.mockaroo.entity.Listing;
import com.worldofbooks.mockaroo.model.Report;
import com.worldofbooks.mockaroo.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ReportProvider {

    @Autowired
    ListingRepository listingRepository;


    public Report getReport() {
        List<Object> monthlyEbayReports = Arrays.asList(getMonthlyEbayData());
        List<Object> monthlyAmazonReports = Arrays.asList(getMonthlyAmazonData());

        Report report = Report.builder()
            .totalListingCount(getTotalListingCount())
            .totalEbayListingCount(getTotalEbayListingCount())
            .totalEbayListingPrice(getTotalEbayListingPrice())
            .avgEbayListingPrice(getAvgEbayListingPrice())
            .totalAmazonListingCount(getTotalAmazonListingCount())
            .totalAmazonListingPrice(getTotalAmazonListingPrice())
            .avgAmazonListingPrice(getAvgAmazonListingPrice())
            .bestListerEmailAddress(getBestListerEmailAddress())
            .monthlyEbayReports(monthlyEbayReports)
            .monthlyAmazonReports(monthlyAmazonReports)
            .build();

        return report;
    }

    private String getBestListerEmailAddress() {
        return listingRepository.getBestEmailAddress();
    }

    private double getAvgAmazonListingPrice() {
        return listingRepository.getAvgListingPriceByMarketPlaceId(Long.parseLong(String.valueOf(1)));
    }

    private double getTotalAmazonListingPrice() {
        double totalAmazonListingPrice = 0;
        List<Listing> amazonListings = listingRepository.getAllByMarketPlaceId(Long.parseLong(String.valueOf(1)));
        for (Listing listing : amazonListings) {
            totalAmazonListingPrice += listing.getListing_price();
        }
        return totalAmazonListingPrice;
    }

    private double getTotalAmazonListingCount() {
        return listingRepository.countListingByMarketPlaceId(Long.parseLong(String.valueOf(1)));
    }

    private double getAvgEbayListingPrice() {
        return listingRepository.getAvgListingPriceByMarketPlaceId(Long.parseLong(String.valueOf(2)));
    }

    private double getTotalEbayListingPrice() {
        double totalEbayListingPrice = 0;
        List<Listing> ebayListings = listingRepository.getAllByMarketPlaceId(Long.parseLong(String.valueOf(2)));
        for (Listing listing : ebayListings) {
            totalEbayListingPrice += listing.getListing_price();
        }
        return totalEbayListingPrice;
    }


    private long getTotalListingCount() {
        return listingRepository.count();
    }

    private int getTotalEbayListingCount() {
        return listingRepository.getAllByMarketPlaceId(Long.parseLong(String.valueOf(2))).size();
    }

    private Object[] getMonthlyEbayData() {
        return listingRepository.getMonthlyEbayData();
    }

    private Object[] getMonthlyAmazonData() {
        return listingRepository.getMonthlyAmazonData();
    }
}
