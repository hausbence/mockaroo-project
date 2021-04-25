package com.worldofbooks.mockaroo.service;

import com.google.gson.Gson;
import com.worldofbooks.mockaroo.entity.Listing;
import com.worldofbooks.mockaroo.model.Report;
import com.worldofbooks.mockaroo.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ReportProvider {

    @Autowired
    ListingRepository listingRepository;


    public Report getReport() {
        Report report = Report.builder()
            .totalListingCount(getTotalListingCount())
            .totalEbayListingCount(getTotalEbayListingCount())
            .totalEbayListingPrice(getTotalEbayListingPrice())
            .avgEbayListingPrice(getAvgEbayListingPrice())
            .totalAmazonListingCount(getTotalAmazonListingCount())
            .totalAmazonListingPrice(getTotalAmazonListingPrice())
            .avgAmazonListingPrice(getAvgAmazonListingPrice())
            .bestListerEmailAddress(getBestListerEmailAddress())
            .build();

        return report;
    }

    private String getBestListerEmailAddress() {
        return listingRepository.getBestEmailAddress();
    }

    private double getAvgAmazonListingPrice() {
        return round(listingRepository.getAvgListingPriceByMarketPlaceId(Long.parseLong(String.valueOf(1))),2);
    }

    private double getTotalAmazonListingPrice() {
        double totalAmazonListingPrice = 0;
        List<Listing> amazonListings = listingRepository.getAllByMarketPlaceId(Long.parseLong(String.valueOf(1)));
        for (Listing listing : amazonListings) {
            totalAmazonListingPrice += listing.getListing_price();
        }
        return round(totalAmazonListingPrice, 2);
    }

    private double getTotalAmazonListingCount() {
        return listingRepository.countListingByMarketPlaceId(Long.parseLong(String.valueOf(1)));
    }

    private double getAvgEbayListingPrice() {
        return round(listingRepository.getAvgListingPriceByMarketPlaceId(Long.parseLong(String.valueOf(2))),2);
    }

    private double getTotalEbayListingPrice() {
        double totalEbayListingPrice = 0;
        List<Listing> ebayListings = listingRepository.getAllByMarketPlaceId(Long.parseLong(String.valueOf(2)));
        for (Listing listing : ebayListings) {
            totalEbayListingPrice += listing.getListing_price();
        }
        return round(totalEbayListingPrice, 2);
    }


    private long getTotalListingCount() {
        return listingRepository.count();
    }

    private int getTotalEbayListingCount() {
        return listingRepository.getAllByMarketPlaceId(Long.parseLong(String.valueOf(2))).size();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
