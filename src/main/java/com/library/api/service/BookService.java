package com.library.api.service;

import com.library.api.dto.BookDTO;
import com.library.api.exception.AlreadyExistsException;
import com.library.api.model.Book;
import com.library.api.model.Branch;
import com.library.api.model.Reader;
import com.library.api.repository.IBookRepository;
import com.library.api.repository.IBranchRepository;
import com.library.api.repository.IReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService {

    @Autowired
    private IBookRepository bookRepo;

    @Autowired
    private IBranchRepository branchRepo;

    @Autowired
    private IReaderRepository readerRepo;

    @Override
    public List<Book> getBooks() {

        return bookRepo.findAll();
    }

    @Override
    public void saveBook(Book book) {
        bookRepo.save(book);
    }

    @Override
    public void deleteBook(Long idBook) {

        bookRepo.deleteById(idBook);
    }

    @Override
    public Book findBook(Long idBook) {

        return bookRepo.findById(idBook).orElse(null);
    }

    @Override
    public BookDTO getBookInfo(Long idBook) {
        Book book = this.findBook(idBook);


        if (book == null) {
            return null;
        }

        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookTitle(book.getTitle());
        bookDTO.setBookGenre(book.getGenre());

        if (book.getReader() != null) {
            String fullName = book.getReader().getFirstName() + " " + book.getReader().getLastName();
            bookDTO.setReaderFullName(fullName);
        } else {
            bookDTO.setReaderFullName("Available (No reader)");
        }

        return bookDTO;
    }

    @Override
    public List<BookDTO> getBooksByGenre(String genre) {

        List<Book> books = bookRepo.findByGenre(genre);


        return books.stream()
                .map(book -> {
                    BookDTO dto = new BookDTO();
                    dto.setBookTitle(book.getTitle());
                    dto.setBookGenre(book.getGenre());

                    dto.setReaderFullName(book.getReader() != null ?
                            book.getReader().getFirstName() + " " + book.getReader().getLastName() :
                            "Available in shelves");
                    return dto;
                })
                .toList();
    }


    @Override
    public void assignBookToBranch(Long idBook, Long idBranch) {

        Book book = this.findBook(idBook);
        if (book == null) {
            throw new AlreadyExistsException("Book not found with ID: " + idBook);
        }


        Branch branch = branchRepo.findById(idBranch).orElse(null);
        if (branch == null) {
            throw new AlreadyExistsException("Branch not found with ID: " + idBranch);
        }


        book.setBranch(branch);


        bookRepo.save(book);
    }




    @Override
    public void borrowBook(Long idBook, Long idReader) {
        Book book = bookRepo.findById(idBook)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // Verificamos si NO está disponible (false) o si ya tiene lector
        if (Boolean.FALSE.equals(book.getAvailable()) || book.getReader() != null) {
            throw new RuntimeException("El libro ya está prestado o no está disponible.");
        }

        Reader reader = readerRepo.findById(idReader)
                .orElseThrow(() -> new RuntimeException("Lector no encontrado"));

        book.setReader(reader);
        book.setAvailable(false); // Lombok genera setAvailable(Boolean b) igual que antes

        bookRepo.save(book);
    }
    @Override
    public void returnBook(Long idBook) {
        // 1. Buscamos el libro
        Book book = bookRepo.findById(idBook)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + idBook));

        // 2. Lógica de devolución
        book.setReader(null);      // Quitamos al lector
        book.setAvailable(true);   // Lo marcamos como disponible de nuevo

        // 3. Guardamos los cambios
        bookRepo.save(book);
    }
}