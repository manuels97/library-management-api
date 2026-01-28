package com.library.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    // Si usas estos nombres, Lombok creará: setBookTitle, setBookGenre, etc.
    private String bookTitle;
    private String bookGenre;
    private String readerFullName;
}