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

    private double runtime;

    private String originalLanguage;

    private String spokenLanguages;

    private double popularity;

    private String status;

    private double voteAverage;

    private int voteCount;

    private int budget;

    private long revenue;

    private String homepage;

    private String imdbId;

    private String productionCompanies;

    private String productionCountries;

    private boolean video;

    private boolean adult;

}
