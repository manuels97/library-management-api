package com.library.api.controller;

import com.library.api.model.Reader;
import com.library.api.service.IReaderService;
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

    @GetMapping("/get")
    public ResponseEntity<List<Reader>> getReaders() {
        List<Reader> list = readerServ.getReaders();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable Long id) {
        Reader reader = readerServ.findReader(id);
        if (reader == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reader, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> saveReader(@RequestBody Reader reader) {
        readerServ.saveReader(reader);
        return new ResponseEntity<>("Reader created successfully", HttpStatus.CREATED); // 201
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReader(@PathVariable Long id) {
        Reader reader = readerServ.findReader(id);

        if (reader == null) {
            return new ResponseEntity<>("Cannot delete: Reader not found", HttpStatus.NOT_FOUND); // 404
        }

        readerServ.deleteReader(id);
        return new ResponseEntity<>("Reader deleted successfully", HttpStatus.OK);
    }
}