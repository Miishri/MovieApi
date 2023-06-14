package org.api.movieApi.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProductionCompanies {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String companyName;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
