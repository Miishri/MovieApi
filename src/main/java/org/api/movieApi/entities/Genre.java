package org.api.movieApi.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String genreName;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
