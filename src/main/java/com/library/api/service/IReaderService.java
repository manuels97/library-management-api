package com.library.api.service;

import com.library.api.dto.ReaderDTO;
import com.library.api.model.Reader;
import java.util.List;

public interface IReaderService {
    List<ReaderDTO> getReaders();
    void deleteReader(Long idReader);
    ReaderDTO findReader(Long idReader); // Cambiado de Reader a ReaderDTO
    void saveReader(Reader reader);
}