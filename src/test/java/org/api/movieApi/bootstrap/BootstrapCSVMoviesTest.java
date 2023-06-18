package org.api.movieApi.bootstrap;

import org.api.movieApi.repository.MovieRepository;
import org.api.movieApi.services.MovieCsvService;
import org.api.movieApi.services.MovieCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(MovieCsvServiceImpl.class)
class BootstrapCSVMoviesTest {

    @Autowired
    private MovieRepository repository;
    @Autowired
    private MovieCsvService movieCsvService;


    BootstrapCSVMovies bootstrap;

    @BeforeEach
    void setUp() {
        bootstrap = new BootstrapCSVMovies(repository, movieCsvService);
    }

    @Test
    @Transactional
    void testBootstrapDataRun() throws Exception {
        bootstrap.run(null);

        assertThat(repository.count()).isEqualTo(45462);
    }

    @Test
    @Transactional
    void testJsonProductionCompanyToString() {
        String companyJson = "[{'name': 'Sandollar Productions', 'id': 5842}, {'name': 'Touchstone Pictures', 'id': 9195}]";
        String resultJson = bootstrap.jsonProductionCompanyToString(companyJson);

        assertThat(resultJson).isEqualTo("Sandollar Productions,Touchstone Pictures");

    }
    @Test
    @Transactional
    void testJsonGenreToPojo() {
        String genreJson = "[{'id': 16, 'name': 'Animation'}, {'id': 35, 'name': 'Comedy'}, {'id': 10751, 'name': 'Family'}]";
        String resultJson = bootstrap.jsonGenreToPojo(genreJson);

        assertThat(resultJson).isEqualTo("Animation,Comedy,Family");

    }
}