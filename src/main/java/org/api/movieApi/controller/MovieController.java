package org.api.movieApi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.api.movieApi.entities.Movie;
import org.api.movieApi.services.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/movie")
public class MovieController {
    public static final String ID_PATH = "/{movieId}";

    private final MovieService movieService;

    @GetMapping(value = "/{movieId}")
    public Movie getMovieById(@PathVariable("movieId") Long movieId) throws HttpNotFoundException {
        return movieService.getMovieById(movieId).orElseThrow(HttpNotFoundException::new);
    }

    @GetMapping
    public Page<Movie> listMovies(@RequestParam(required = false) Integer pageNumber,
                                  @RequestParam(required = false) Integer pageSize) {
        return movieService.listMovies(pageNumber, pageSize);
    }

    @PostMapping
    public Movie saveNewMovie(@RequestBody Movie movie) {
        return movieService.saveNewMovie(movie);
    }

    @PutMapping(value = ID_PATH  )
    public ResponseEntity<Movie> updateMovieById(@PathVariable("movieId") Long movieId, @RequestBody Movie movie) throws HttpNotFoundException {
        Optional<Movie> foundMovie = movieService.updateMovieById(movieId, movie);

        if (foundMovie.isEmpty()) {
            throw new HttpNotFoundException();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = ID_PATH)
    public ResponseEntity<Void> deleteMovieById(@PathVariable("movieId") Long movieId) throws HttpNotFoundException {
        if (! movieService.deleteById(movieId)) throw new HttpNotFoundException();

        return ResponseEntity.noContent().build();
    }

}
