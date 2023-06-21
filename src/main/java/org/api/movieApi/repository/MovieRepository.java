package org.api.movieApi.repository;

import org.api.movieApi.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findAllByTitle(String title);

    List<Movie> findAllByRuntime(double runtime);

    List<Movie> findAllByOriginalLanguage(String originalLanguage);

    List<Movie> findAllByAdult(boolean adult);
}
