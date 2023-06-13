package org.api.movieApi.services;

import com.opencsv.bean.CsvToBeanBuilder;
import org.api.movieApi.model.MovieCSV;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
@Primary
public class MovieCsvServiceImpl implements MovieCsvService {
    @Override
    public List<MovieCSV> convertToMovieCsv(File csvMovieFile) {

        try {

            return new CsvToBeanBuilder<MovieCSV>(new FileReader(csvMovieFile))
                    .withType(MovieCSV.class)
                    .build().parse();

        }catch (FileNotFoundException fileNotFoundException) {
            throw new RuntimeException(fileNotFoundException);
        }
    }
}
