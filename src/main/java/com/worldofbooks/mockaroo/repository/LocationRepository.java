package com.worldofbooks.mockaroo.repository;

import com.worldofbooks.mockaroo.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
