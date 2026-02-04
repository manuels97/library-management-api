package com.library.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título no puede estar vacío")
    @Size(min = 2, max = 100)
    private String title;

    @NotBlank(message = "El autor es obligatorio")
    private String author;

    @NotBlank(message = "El género es obligatorio")
    private String genre;

    @NotNull
    private Boolean available = true;

    @ManyToOne
    @JoinColumn(name = "id_branch")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "id_reader")
    private Reader reader;
}