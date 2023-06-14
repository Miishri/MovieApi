package org.api.movieApi.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductionCountries {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "iso_3166_1")
    private String iso;

    private String countryName;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}