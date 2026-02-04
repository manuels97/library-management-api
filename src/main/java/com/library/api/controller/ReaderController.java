package com.library.api.controller;

import com.library.api.dto.ReaderDTO;
import com.library.api.model.Reader;
import com.library.api.service.IReaderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/readers")
public class ReaderController {

    @Autowired
    private IReaderService readerServ;

    // 1. CORREGIDO: El retorno ahora es List<ReaderDTO>
    @GetMapping("/get")
    public ResponseEntity<List<ReaderDTO>> getReaders() {
        List<ReaderDTO> list = readerServ.getReaders();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 2. CORREGIDO: findReader ahora devuelve un ReaderDTO
    @GetMapping("/get/{id}")
    public ResponseEntity<ReaderDTO> getReaderById(@PathVariable Long id) {
        ReaderDTO readerDTO = readerServ.findReader(id);
        return new ResponseEntity<>(readerDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> saveReader(@Valid @RequestBody Reader reader) {
        readerServ.saveReader(reader);
        return new ResponseEntity<>("Reader created successfully", HttpStatus.CREATED);
    }

    // 3. LIMPIEZA: No hace falta buscar el lector antes de borrar.
    // El Service ya lanza la excepción si no existe.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReader(@PathVariable Long id) {
        readerServ.deleteReader(id);
        return new ResponseEntity<>("Reader deleted successfully", HttpStatus.OK);
    }
}