package org.api.movieApi.repository;

import org.api.movieApi.entities.SpokenLanguages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpokenLanguagesRepository extends JpaRepository<SpokenLanguages, Long> {
}
