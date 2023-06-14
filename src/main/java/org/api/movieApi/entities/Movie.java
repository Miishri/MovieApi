package org.api.movieApi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "movie_id")
    private Long id;

    private String title;

    private String originalTitle;

    @Column(length = 1000)
    private String overview;

    @OneToMany(mappedBy = "movie")
    private Set<Genre> genres;

    private String releaseDate;

    private Double runtime;

    private String originalLanguage;

    @OneToMany(mappedBy = "movie")
    private Set<SpokenLanguages> spokenLanguages;

    private Double popularity;

    private String status;

    private Double voteAverage;

    private Integer voteCount;

    private Integer budget;

    private Long revenue;

    private String homepage;

    private String imdbId;

    @OneToMany(mappedBy = "movie")
    private Set<ProductionCompanies> productionCompanies;

    @OneToMany(mappedBy = "movie")
    private Set<ProductionCountries> productionCountries;

    private Boolean video;

    private Boolean adult;

}
