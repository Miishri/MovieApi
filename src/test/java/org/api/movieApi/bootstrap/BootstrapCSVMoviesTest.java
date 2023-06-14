package org.api.movieApi.bootstrap;

import org.api.movieApi.entities.Genre;
import org.api.movieApi.entities.ProductionCompanies;
import org.api.movieApi.entities.ProductionCountries;
import org.api.movieApi.entities.SpokenLanguages;
import org.api.movieApi.repository.*;
import org.api.movieApi.services.MovieCsvService;
import org.api.movieApi.services.MovieCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(MovieCsvServiceImpl.class)
class BootstrapCSVMoviesTest {

    @Autowired
    private MovieRepository repository;
    @Autowired
    private MovieCsvService movieCsvService;


    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ProductionCountriesRepository countriesRepository;
    @Autowired
    private ProductionCompaniesRepository companiesRepository;
    @Autowired
    private SpokenLanguagesRepository languagesRepository;
    BootstrapCSVMovies bootstrap;

    @BeforeEach
    void setUp() {
        bootstrap = new BootstrapCSVMovies(repository, movieCsvService, genreRepository, countriesRepository, companiesRepository, languagesRepository);
    }

    @Test
    @Transactional
    void testBootstrapDataRun() throws Exception {
        bootstrap.run(null);

        assertThat(repository.count()).isEqualTo(45462);
    }
    @Test
    @Transactional
    void testJsonCompanyToPojo() {

        ProductionCompanies productionCountries = bootstrap
                .jsonCompanyToPojo("[{'name': 'Pixar Animation Studios', 'id': 3}]", null)
                .stream()
                .iterator()
                .next();

        assertThat(productionCountries.getCompanyName()).isEqualTo("Pixar Animation Studios");
    }

    @Test
    @Transactional
    void testJsonCountryToPojo() {
        ProductionCountries productionCountries = bootstrap
                .jsonCountryToPojo("[{'iso_3166_1': 'US', 'name': 'United States of America'}]", null)
                .stream()
                .iterator()
                .next();

        assertThat(productionCountries.getCountryName()).isEqualTo("United States of America");
        assertThat(productionCountries.getIso()).isEqualTo("US");
    }

    @Test
    @Transactional
    void testJsonLanguagesToPojo() {
        SpokenLanguages spokenLanguage = bootstrap
                .jsonLanguageToPojo("[{'iso_639_1': 'en', 'name': 'English'}]", null)
                .stream()
                .iterator()
                .next();

        assertThat(spokenLanguage.getLanguage()).isEqualTo("English");
    }

    @Test
    @Transactional
    void testJsonGenreToPojo() {
        Iterator<Genre> genreIterator = bootstrap
                .jsonGenreToPojo("[{'id': 16, 'name': 'Animation'}, {'id': 35, 'name': 'Comedy'}, {'id': 10751, 'name': 'Family'}]", null)
                .stream()
                .iterator();

        Genre genre = genreIterator.next();
        assertThat(genre.getGenreName()).isEqualTo("Animation");
        genre = genreIterator.next();
        assertThat(genre.getGenreName()).isEqualTo("Comedy");
        genre = genreIterator.next();
        assertThat(genre.getGenreName()).isEqualTo("Family");

    }
}