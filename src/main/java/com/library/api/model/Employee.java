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
    private String position;
    @ManyToOne
    @JoinColumn(name = "id_branch")
    private Branch branch;
}