package org.api.movieApi.repository;

import org.api.movieApi.entities.ProductionCompanies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionCompaniesRepository extends JpaRepository<ProductionCompanies, Long> {
}
