package org.api.movieApi.services;


import org.api.movieApi.model.MovieCSV;

import java.io.File;
import java.util.List;

public interface MovieCsvService {
    List<MovieCSV> convertToMovieCsv(File csvMovieFile);
}
