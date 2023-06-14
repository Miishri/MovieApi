package org.api.movieApi.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpokenLanguages {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String language;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
