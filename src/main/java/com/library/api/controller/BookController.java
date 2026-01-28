package com.library.api.controller;

import com.library.api.dto.BookDTO;
import com.library.api.model.Book;
import com.library.api.service.IBookService;
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
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(bookServ.getBooks());
    }

    @PostMapping("/create")
    public ResponseEntity<String> saveBook(@RequestBody Book book) {
        bookServ.saveBook(book);
        return new ResponseEntity<>("Book registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<BookDTO> getBookInfo(@PathVariable Long id) {
        BookDTO dto = bookServ.getBookInfo(id);

        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        Book book = bookServ.findBook(id);

        if (book == null) {
            return new ResponseEntity<>("Cannot delete: Book not found", HttpStatus.NOT_FOUND);
        }

        bookServ.deleteBook(id);
        return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/genre/{genreName}")
    public ResponseEntity<List<BookDTO>> getBooksByGenre(@PathVariable String genreName) {
        List<BookDTO> list = bookServ.getBooksByGenre(genreName);

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK); // Código 200
    }

    @PatchMapping("/{idBook}/assign-branch/{idBranch}")
    public ResponseEntity<String> assignBranch(@PathVariable Long idBook, @PathVariable Long idBranch) {
        bookServ.assignBookToBranch(idBook, idBranch);
        return ResponseEntity.ok("Book " + idBook + " successfully assigned to branch " + idBranch);
    }

    @PatchMapping("/{idBook}/borrow/{idReader}")
    public ResponseEntity<String> borrowBook(@PathVariable Long idBook, @PathVariable Long idReader) {
        try {
            bookServ.borrowBook(idBook, idReader);
            return ResponseEntity.ok("Libro prestado con éxito.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PatchMapping("/{idBook}/return")
    public ResponseEntity<String> returnBook(@PathVariable Long idBook) {
        try {
            bookServ.returnBook(idBook);
            return ResponseEntity.ok("Libro devuelto y disponible para el siguiente lector.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}