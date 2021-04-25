package com.worldofbooks.mockaroo.repository;

import com.worldofbooks.mockaroo.entity.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ListingStatusRepository extends JpaRepository<ListingStatus, Long> {

    Optional<ListingStatus> findById(UUID id);

}
