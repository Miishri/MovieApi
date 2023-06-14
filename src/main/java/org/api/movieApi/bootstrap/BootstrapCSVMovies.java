package org.api.movieApi.bootstrap;

import lombok.RequiredArgsConstructor;
import org.api.movieApi.entities.Movie;
import org.api.movieApi.repository.MovieRepository;
import org.api.movieApi.services.MovieCsvService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Component
@RequiredArgsConstructor
public class BootstrapCSVMovies implements CommandLineRunner {

    private final MovieRepository repository;
    private final MovieCsvService service;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadMoviesCsvData();
    }

    private void loadMoviesCsvData() throws FileNotFoundException {
        if (repository.count() == 0) {
            File file = ResourceUtils.getFile("classpath:csvdata/movies_metadata.csv");

            service.convertToMovieCsv(file).forEach(movieCSV -> {

                repository.save(Movie.builder()
                    .title(movieCSV.getTitle())
                    .originalTitle(movieCSV.getOriginalTitle())
                    .overview(movieCSV.getOverview())
                    .genres(movieCSV.getGenres())
                    .releaseDate(movieCSV.getReleaseDate())
                    .runtime(movieCSV.getRuntime())
                    .originalLanguage(movieCSV.getOriginalLanguage())
                    .spokenLanguages(movieCSV.getSpokenLanguages())
                    .popularity(movieCSV.getPopularity())
                    .status(movieCSV.getStatus())
                    .voteAverage(movieCSV.getVoteAverage())
                    .voteCount(movieCSV.getVoteCount())
                    .budget(movieCSV.getBudget())
                    .revenue(movieCSV.getRevenue())
                    .homepage(movieCSV.getHomepage())
                    .imdbId(movieCSV.getImdbId())
                    .productionCompanies(movieCSV.getProductionCompanies())
                    .productionCountries(movieCSV.getProductionCountries())
                    .video(movieCSV.getVideo())
                    .adult(movieCSV.getAdult())
                    .build());
            });

        }
    }
}
