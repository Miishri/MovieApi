package org.api.movieApi.repository;

import org.api.movieApi.entities.ProductionCountries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionCountriesRepository extends JpaRepository<ProductionCountries, Long> {
}
