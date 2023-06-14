package org.api.movieApi.bootstrap;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.api.movieApi.entities.*;
import org.api.movieApi.model.MovieCSV;
import org.api.movieApi.repository.*;
import org.api.movieApi.services.MovieCsvService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class BootstrapCSVMovies implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final MovieCsvService service;

    private final GenreRepository genreRepository;
    private final ProductionCountriesRepository countriesRepository;
    private final ProductionCompaniesRepository companiesRepository;
    private final SpokenLanguagesRepository languagesRepository;
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

                movie.setGenres(jsonGenreToPojo(movieCSV.getGenres(), movie));
                movie.setSpokenLanguages(jsonLanguageToPojo(movieCSV.getSpokenLanguages(), movie));
                movie.setProductionCompanies(jsonCompanyToPojo(movieCSV.getProductionCompanies(), movie));
                movie.setProductionCountries(jsonCountryToPojo(movieCSV.getProductionCountries(), movie));

                movieRepository.save(movie);
            });

            System.out.println("BOOTSTRAPPING DATA SUCCESSFUL");

        }
    }

    public Set<ProductionCompanies> jsonCompanyToPojo(String companiesJson, Movie movie) {
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        try {
            JsonNode jsonNode = objectMapper.readTree(companiesJson);
            Set<ProductionCompanies> productionCompaniesSet = new HashSet<>();
            for (JsonNode companyNode : jsonNode) {
                ProductionCompanies productionCompany = ProductionCompanies.builder()
                        .companyName(companyNode.get("name").asText())
                        .movie(movie)
                        .build();
                productionCompaniesSet.add(productionCompany);
                companiesRepository.save(productionCompany);
            }

            return productionCompaniesSet;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<ProductionCountries> jsonCountryToPojo(String countriesJson, Movie movie) {
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        try {
            JsonNode jsonNode = objectMapper.readTree(countriesJson);
            Set<ProductionCountries> productionCountriesSet = new HashSet<>();
            for (JsonNode countryNode : jsonNode) {
                ProductionCountries productionCountry = ProductionCountries.builder()
                        .countryName(countryNode.get("name").asText())
                        .iso(countryNode.get("iso_3166_1").asText())
                        .movie(movie)
                        .build();
                productionCountriesSet.add(productionCountry);
                countriesRepository.save(productionCountry);
            }

            return productionCountriesSet;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Genre> jsonGenreToPojo(String genreJson, Movie movie) {
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        try {
            JsonNode jsonNode = objectMapper.readTree(genreJson);
            Set<Genre> genreSet = new HashSet<>();
            for (JsonNode genreNode : jsonNode) {
                Genre genre = Genre.builder()
                        .genreName(genreNode.get("name").asText())
                        .movie(movie)
                        .build();
                genreSet.add(genre);
                genreRepository.save(genre);
            }

            return genreSet;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<SpokenLanguages> jsonLanguageToPojo(String languageJson, Movie movie) {
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        try {
            JsonNode jsonNode = objectMapper.readTree(languageJson);
            Set<SpokenLanguages> spokenLanguagesSet = new HashSet<>();
            for (JsonNode genreNode : jsonNode) {
                SpokenLanguages spokenLanguage = SpokenLanguages.builder()
                        .language(genreNode.get("name").asText())
                        .movie(movie)
                        .build();
                spokenLanguagesSet.add(spokenLanguage);
                languagesRepository.save(spokenLanguage);
            }

            return spokenLanguagesSet;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
