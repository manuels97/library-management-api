package com.library.api.service;

import com.library.api.model.Reader;
import java.util.List;

public interface IReaderService {
    public List<Reader> getReaders();
    public void saveReader(Reader reader);
    public void deleteReader(Long idReader);
    public Reader findReader(Long idReader);
}