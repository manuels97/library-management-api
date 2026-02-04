package com.library.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReaderDTO {
    private Long idReader;
    private String readerFullName;
    private String readerDni;
    private String readerPhone;
}