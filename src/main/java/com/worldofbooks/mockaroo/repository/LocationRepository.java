package com.worldofbooks.mockaroo.repository;

import com.worldofbooks.mockaroo.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findById(UUID id);
}
