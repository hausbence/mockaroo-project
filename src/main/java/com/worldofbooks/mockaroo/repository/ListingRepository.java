package com.worldofbooks.mockaroo.repository;

import com.worldofbooks.mockaroo.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> getAllByMarketPlaceId(Long marketplaceId);

    @Query(nativeQuery = true, value = "SELECT AVG(listing_price) FROM Listing WHERE Listing.market_place_id = ?1")
    double getAvgListingPriceByMarketPlaceId(Long marketplaceId);

    double countListingByMarketPlaceId(Long marketplaceId);

    @Query(nativeQuery = true, value = "SELECT owner_email_address  FROM listing GROUP BY owner_email_address, (quantity * listing_price) ORDER BY  (quantity * listing_price) DESC LIMIT 1")
    String getBestEmailAddress();

    /**
     * ○ Total eBay listing count per month
     * ○ Total eBay listing price per month
     * ○ Average eBay listing price per month
     */
    @Query(nativeQuery = true, value =
        "SELECT cast(date_trunc('month', cast(LISTING.UPLOAD_TIME as timestamp)) as date) as date, " +
            "COUNT(*) as listing_per_month,sum(LISTING.LISTING_PRICE) as total_price_monthly," +
            "AVG(LISTING.LISTING_PRICE) as avg_listing_price_monthly from LISTING join marketplace on listing.market_place_id = marketplace.id" +
            " GROUP by date_trunc('month', cast(LISTING.UPLOAD_TIME as timestamp)), marketplace.marketplace_name having marketplace.MARKETPLACE_NAME = 'EBAY'")
    Object[] getMonthlyEbayData();

    /**
     * ○ Average Amazon listing price per month
     * ○ Total Amazon listing count per month
     * ○ Total Amazon listing price per month
     */
    @Query(nativeQuery = true, value =
        "SELECT cast(date_trunc('month', cast(LISTING.UPLOAD_TIME as timestamp)) as date) as date," +
        "COUNT(*) as listing_per_month,sum(LISTING.LISTING_PRICE) as total_price_monthly," +
        "AVG(LISTING.LISTING_PRICE) as avg_listing_price_monthly from LISTING join marketplace on listing.market_place_id = marketplace.id" +
        " GROUP BY date_trunc('month', cast(LISTING.UPLOAD_TIME as timestamp)), marketplace.marketplace_name HAVING marketplace.MARKETPLACE_NAME = 'AMAZON'")
    Object[] getMonthlyAmazonData();
}
