package com.library.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBook;
    private String title;
    private String author;
    private String genre;

    @OneToOne
    @JoinColumn(name = "id_reader", referencedColumnName = "idReader")
    private Reader reader;

    @ManyToOne
    @JoinColumn(name = "id_branch") // Nombre de la columna en la tabla de MySQL
    private Branch branch;

    private boolean available = true; // Por defecto, el libro está disponible
}