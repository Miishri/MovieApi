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
}