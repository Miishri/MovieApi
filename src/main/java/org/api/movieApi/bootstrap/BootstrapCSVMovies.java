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
        return jsonToString(companyJson);
    }

    public String jsonGenreToPojo(String genreJson) {
        return jsonToString(genreJson);
    }

    private String jsonToString(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            StringBuilder string = new StringBuilder();
            for (JsonNode node : jsonNode) {
                string.append(node.get("name").asText());
                string.append(",");
            }
            if (!string.isEmpty()) {
                string.delete(string.length()-1, string.length());
            }else {
                string.append("None");
            }

            return string.toString();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
