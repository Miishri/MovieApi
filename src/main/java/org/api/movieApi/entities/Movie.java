package org.api.movieApi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String title;

    private String originalTitle;

    private String overview;

    private String genres;

    private String releaseDate;

    private Double runtime;

    private String originalLanguage;

    private String spokenLanguages;

    private Double popularity;

    private String status;

    private Double voteAverage;

    private Integer voteCount;

    private Integer budget;

    private Long revenue;

    private String homepage;

    private String imdbId;

    private String productionCompanies;

    private String productionCountries;

    private Boolean video;

    private Boolean adult;

}
