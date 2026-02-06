package com.library.api.service;

import com.library.api.dto.ReaderDTO;
import com.library.api.exception.AlreadyExistsException;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.model.Reader;
import com.library.api.repository.IReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReaderService implements IReaderService {

    @Autowired
    private IReaderRepository readerRepo;

    @Override
    public List<ReaderDTO> getReaders() {
        List<Reader> readers = readerRepo.findAll();
        return readers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReader(Long idReader) {
        if (!readerRepo.existsById(idReader)) {
            throw new ResourceNotFoundException("No se puede eliminar: Lector no encontrado con ID: " + idReader);
        }
        readerRepo.deleteById(idReader);
    }

    @Override
    public ReaderDTO findReader(Long idReader) {
        Reader reader = readerRepo.findById(idReader)
                .orElseThrow(() -> new ResourceNotFoundException("Lector no encontrado con ID: " + idReader));
        return convertToDTO(reader);
    }

    @Override
    public void saveReader(Reader reader) {
        Reader existingReader = readerRepo.findByDni(reader.getDni());
        if (existingReader != null) {
            throw new AlreadyExistsException("A reader with DNI " + reader.getDni() + " already exists.");
        }
        readerRepo.save(reader);
    }

    private ReaderDTO convertToDTO(Reader reader) {
        ReaderDTO dto = new ReaderDTO();
        dto.setIdReader(reader.getId());
        dto.setReaderFullName(reader.getFirstName() + " " + reader.getLastName());
        dto.setReaderDni(reader.getDni());
        dto.setReaderPhone(reader.getPhoneNumber());
        return dto;
    }
}