package com.worldofbooks.mockaroo.repository;

import com.worldofbooks.mockaroo.entity.Listing;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.ResultSet;
import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> getAllByMarketPlaceId(Long marketplaceId);

    @Query(nativeQuery = true, value = "SELECT AVG(listing_price) FROM Listing WHERE Listing.market_place_id = ?1" )
    double getAvgListingPriceByMarketPlaceId(Long marketplaceId);

    double countListingByMarketPlaceId(Long marketplaceId);

    @Query(nativeQuery = true, value = "SELECT owner_email_address  FROM listing GROUP BY owner_email_address, (quantity * listing_price) ORDER BY  (quantity * listing_price) DESC LIMIT 1")
    String getBestEmailAddress();

    @Query(nativeQuery = true, value = "select cast(date_trunc('month', cast(LISTING.UPLOAD_TIME as timestamp)) as date) as date,count(*) as listing_per_month,sum(LISTING.LISTING_PRICE) as total_price_monthly,avg(LISTING.LISTING_PRICE) as avg_listing_price_monthly from LISTING join marketplace on listing.market_place_id = marketplace.id group by date_trunc('month', cast(LISTING.UPLOAD_TIME as timestamp)), marketplace.marketplace_name having marketplace.MARKETPLACE_NAME = 'EBAY'")
    Object[] getMonthlyEbayData();

    @Query(nativeQuery = true, value = "select cast(date_trunc('month', cast(LISTING.UPLOAD_TIME as timestamp)) as date) as date,count(*) as listing_per_month,sum(LISTING.LISTING_PRICE) as total_price_monthly,avg(LISTING.LISTING_PRICE) as avg_listing_price_monthly from LISTING join marketplace on listing.market_place_id = marketplace.id group by date_trunc('month', cast(LISTING.UPLOAD_TIME as timestamp)), marketplace.marketplace_name having marketplace.MARKETPLACE_NAME = 'AMAZON'")
    Object[] getMonthlyAmazonData();
}
