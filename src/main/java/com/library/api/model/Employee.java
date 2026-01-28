package com.library.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmployee;

    private String firstName;
    private String lastName;
    private String position; // Ej: "Admin", "Librarian", "Cleaner"

    // Relación: Muchos empleados trabajan en una Sede
    @ManyToOne
    @JoinColumn(name = "id_branch")
    private Branch branch;
}