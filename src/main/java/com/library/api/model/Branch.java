package com.library.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBranch;
    private String name;
    private String address;


    @OneToMany(mappedBy = "branch")
    @JsonIgnore // Importante para evitar recursión infinita en el JSON
    private List<Book> books;
}