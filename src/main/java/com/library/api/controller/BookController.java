package com.library.api.controller;

import com.library.api.dto.BookDTO;
import com.library.api.model.Book;
import com.library.api.service.IBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private IBookService bookServ;


    @GetMapping("/get")
    public ResponseEntity<List<BookDTO>> getBooks() {
        return ResponseEntity.ok(bookServ.getBooks());
    }

    @PostMapping("/create")
    public ResponseEntity<String> saveBook(@Valid @RequestBody Book book) {
        bookServ.saveBook(book);
        return new ResponseEntity<>("Book registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<BookDTO> getBookInfo(@PathVariable Long id) {

        return ResponseEntity.ok(bookServ.findBook(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {

        bookServ.deleteBook(id);
        return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/genre/{genreName}")
    public ResponseEntity<List<BookDTO>> getBooksByGenre(@PathVariable String genreName) {
        List<BookDTO> list = bookServ.getBooksByGenre(genreName);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PatchMapping("/{idBook}/assign-branch/{idBranch}")
    public ResponseEntity<String> assignBranch(@PathVariable Long idBook, @PathVariable Long idBranch) {
        bookServ.assignBookToBranch(idBook, idBranch);
        return ResponseEntity.ok("Book " + idBook + " successfully assigned to branch " + idBranch);
    }


    @PatchMapping("/{idBook}/borrow/{idReader}")
    public ResponseEntity<String> borrowBook(@PathVariable Long idBook, @PathVariable Long idReader) {
        bookServ.borrowBook(idBook, idReader);
        return ResponseEntity.ok("Libro prestado con éxito.");
    }

    @PatchMapping("/{idBook}/return")
    public ResponseEntity<String> returnBook(@PathVariable Long idBook) {
        bookServ.returnBook(idBook);
        return ResponseEntity.ok("Libro devuelto y disponible para el siguiente lector.");
    }
}