package com.worldofbooks.mockaroo.repository;

import com.worldofbooks.mockaroo.entity.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingStatusRepository extends JpaRepository<ListingStatus, Long> {
}
