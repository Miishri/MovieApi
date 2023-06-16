package org.api.movieApi.repository;

import org.api.movieApi.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findAllByTitle(String title,
                               Pageable pageable);

    Page<Movie> findAllByRuntime(Double runtime, Pageable pageable);

    Page<Movie> findAllByOriginalLanguage(String originalLanguage, Pageable pageable);

    Page<Movie> findAllByAdult(Boolean adult, Pageable pageable);
}
