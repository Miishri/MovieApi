package org.api.movieApi.bootstrap;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.api.movieApi.entities.Movie;
import org.api.movieApi.model.MovieCSV;
import org.api.movieApi.repository.MovieRepository;
import org.api.movieApi.services.MovieCsvService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapCSVMovies implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final MovieCsvService service;
    private ObjectMapper objectMapper;
    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadMoviesCsvData();
    }

    private void loadMoviesCsvData() throws FileNotFoundException {
        if (movieRepository.count() == 0) {
            File file = ResourceUtils.getFile("classpath:csvdata/movies_metadata.csv");

            List<MovieCSV> movieList = service.convertToMovieCsv(file);

            movieList.forEach(movieCSV -> {
                Movie movie = Movie.builder()
                        .title(movieCSV.getTitle())
                        .originalTitle(movieCSV.getOriginalTitle())
                        .overview(movieCSV.getOverview())
                        .releaseDate(movieCSV.getReleaseDate())
                        .runtime(movieCSV.getRuntime())
                        .originalLanguage(movieCSV.getOriginalLanguage())
                        .popularity(movieCSV.getPopularity())
                        .status(movieCSV.getStatus())
                        .voteAverage(movieCSV.getVoteAverage())
                        .voteCount(movieCSV.getVoteCount())
                        .budget(movieCSV.getBudget())
                        .revenue(movieCSV.getRevenue())
                        .homepage(movieCSV.getHomepage())
                        .imdbId(movieCSV.getImdbId())
                        .video(movieCSV.getVideo())
                        .adult(movieCSV.getAdult())
                        .build();

                movie.setProductionCompanies(jsonProductionCompanyToString(movieCSV.getProductionCompanies()));
                movie.setGenres(jsonGenreToPojo(movieCSV.getGenres()));
                movieRepository.save(movie);

            });

            System.out.println("BOOTSTRAPPING DATA SUCCESSFUL");

        }
    }

    public String jsonProductionCompanyToString(String companyJson) {
        return getString(companyJson);
    }

    public String jsonGenreToPojo(String genreJson) {
        return getString(genreJson);
    }

    private String getString(String genreJson) {
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        try {
            JsonNode jsonNode = objectMapper.readTree(genreJson);
            StringBuilder genre = new StringBuilder();
            for (JsonNode genreNode : jsonNode) {
                genre.append(genreNode.get("name").asText());
                genre.append(",");
            }
            if (!genre.isEmpty()) {
                genre.delete(genre.length()-1, genre.length());
            }else {
                genre.append("None");
            }

            return genre.toString();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
