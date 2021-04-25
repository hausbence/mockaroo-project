package com.worldofbooks.mockaroo.repository;

import com.worldofbooks.mockaroo.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long> {
}
