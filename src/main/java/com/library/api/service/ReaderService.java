package com.library.api.service;

import com.library.api.exception.AlreadyExistsException;
import com.library.api.model.Reader;
import com.library.api.repository.IReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service // ¡No olvides esta anotación!
public class ReaderService implements IReaderService {

    @Autowired
    private IReaderRepository readerRepo;

    @Override
    public List<Reader> getReaders() {
        return readerRepo.findAll();
    }


    @Override
    public void deleteReader(Long idReader) {
        readerRepo.deleteById(idReader);
    }

    @Override
    public Reader findReader(Long idReader) {
        return readerRepo.findById(idReader).orElse(null);
    }

    @Override
    public void saveReader(Reader reader) {

        Reader existingReader = readerRepo.findByDni(reader.getDni());


        if (existingReader != null) {
            throw new AlreadyExistsException("A reader with DNI " + reader.getDni() + " already exists.");
        }


        readerRepo.save(reader);
    }
}