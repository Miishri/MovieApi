package org.api.movieApi.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie")
public class Movie {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String title;

    private String originalTitle;

    @Column(length = 1500)
    private String overview;

    @Column(length = 1500)
    private String genres;

    private String releaseDate;

    private double runtime;

    private String originalLanguage;

    private Double popularity;

    private String status;

    private Double voteAverage;

    private Integer voteCount;

    private int budget;

    private Long revenue;

    private String homepage;

    private String imdbId;

    @Column(length = 1500)
    private String productionCompanies;

    private boolean adult;

}
