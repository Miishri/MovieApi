package org.api.movieApi.repository;

import org.api.movieApi.entities.ProductionCountries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCountriesRepository extends JpaRepository<ProductionCountries, Long> {
}
