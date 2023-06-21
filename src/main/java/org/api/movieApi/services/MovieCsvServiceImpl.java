package org.api.movieApi.services;

import com.opencsv.bean.CsvToBeanBuilder;
import org.api.movieApi.model.MovieCSV;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class MovieCsvServiceImpl implements MovieCsvService {
    @Override
    public List<MovieCSV> convertToMovieCsv(File csvMovieFile) {

        try (FileReader fileReader = new FileReader(csvMovieFile)) {

            return new CsvToBeanBuilder<MovieCSV>(fileReader)
                    .withType(MovieCSV.class)
                    .build()
                    .parse();


        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

    }
}
