package org.api.movieApi.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieCSV {

    @CsvBindByName
    private Boolean adult;

    @CsvBindByName(column = "belongs_to_collection")
    private String belongsToCollection;

    @CsvBindByName
    private Integer budget;

    @CsvBindByName
    private String genres;

    @CsvBindByName
    private String homepage;

    @CsvBindByName
    private Integer id;

    @CsvBindByName(column = "imdb_id")
    private String imdbId;

    @CsvBindByName(column = "original_language")
    private String originalLanguage;

    @CsvBindByName(column = "original_title")
    private String originalTitle;

    @CsvBindByName
    private String overview;

    @CsvBindByName
    private Double popularity;

    @CsvBindByName(column = "poster_path")
    private String posterPath;

    @CsvBindByName(column = "production_companies")
    private String productionCompanies;

    @CsvBindByName(column = "production_countries")
    private String productionCountries;

    @CsvBindByName(column = "release_date")
    private String releaseDate;

    @CsvBindByName
    private Long revenue;

    @CsvBindByName
    private Double runtime;

    @CsvBindByName(column = "spoken_languages")
    private String spokenLanguages;

    @CsvBindByName
    private String status;

    @CsvBindByName
    private String tagline;

    @CsvBindByName
    private String title;

    @CsvBindByName(column = "vote_average")
    private Double voteAverage;

    @CsvBindByName(column = "vote_count")
    private Integer voteCount;
}
