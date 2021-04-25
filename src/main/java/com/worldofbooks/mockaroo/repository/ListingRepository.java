package com.worldofbooks.mockaroo.repository;

import com.worldofbooks.mockaroo.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> getAllByMarketPlaceId(Long marketplaceId);

    @Query(nativeQuery = true, value = "SELECT AVG(listing_price) FROM Listing WHERE Listing.market_place_id = ?1" )
    double getAvgListingPriceByMarketPlaceId(Long marketplaceId);

    double countListingByMarketPlaceId(Long marketplaceId);

    @Query(nativeQuery = true, value = "SELECT owner_email_address  FROM listing GROUP BY owner_email_address, (quantity * listing_price) ORDER BY  (quantity * listing_price) DESC LIMIT 1")
    String getBestEmailAddress();
}
