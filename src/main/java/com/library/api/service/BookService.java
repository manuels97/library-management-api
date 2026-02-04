package com.library.api.service;

import com.library.api.dto.BookDTO;
import com.library.api.exception.BadRequestException;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.model.Book;
import com.library.api.model.Branch;
import com.library.api.model.Reader;
import com.library.api.repository.IBookRepository;
import com.library.api.repository.IBranchRepository;
import com.library.api.repository.IReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {

    @Autowired
    private IBookRepository bookRepo;

    @Autowired
    private IBranchRepository branchRepo;

    @Autowired
    private IReaderRepository readerRepo;

    @Override
    public List<BookDTO> getBooks() {

        List<Book> books = bookRepo.findAll();

        return books.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public void saveBook(Book book) {
        bookRepo.save(book);
    }

    @Override
    public void deleteBook(Long idBook) {
        if (!bookRepo.existsById(idBook)) {
            throw new ResourceNotFoundException("No se puede eliminar: Libro no encontrado ID: " + idBook);
        }
        bookRepo.deleteById(idBook);
    }

    @Override
    public BookDTO findBook(Long idBook) {
        Book book = bookRepo.findById(idBook)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado ID: " + idBook));
        return convertToDTO(book);
    }

    @Override
    public BookDTO getBookInfo(Long idBook) {
        return this.findBook(idBook);
    }

    @Override
    public List<BookDTO> getBooksByGenre(String genre) {
        return bookRepo.findByGenre(genre).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void assignBookToBranch(Long idBook, Long idBranch) {
        Book book = bookRepo.findById(idBook)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado ID: " + idBook));

        Branch branch = branchRepo.findById(idBranch)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada ID: " + idBranch));

        book.setBranch(branch);
        bookRepo.save(book);
    }

    @Override
    @Transactional
    public void borrowBook(Long idBook, Long idReader) {
        Book book = bookRepo.findById(idBook)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));

        if (Boolean.FALSE.equals(book.getAvailable()) || book.getReader() != null) {
            throw new BadRequestException("El libro '" + book.getTitle() + "' ya está prestado.");
        }

        Reader reader = readerRepo.findById(idReader)
                .orElseThrow(() -> new ResourceNotFoundException("Lector no encontrado"));

        book.setReader(reader);
        book.setAvailable(false);
        bookRepo.save(book);
    }

    @Override
    @Transactional
    public void returnBook(Long idBook) {
        Book book = bookRepo.findById(idBook)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));

        if (Boolean.TRUE.equals(book.getAvailable())) {
            throw new BadRequestException("El libro '" + book.getTitle() + "' ya está disponible.");
        }

        book.setReader(null);
        book.setAvailable(true);
        bookRepo.save(book);
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setBookTitle(book.getTitle());
        dto.setBookGenre(book.getGenre());

        if (book.getReader() != null) {
            dto.setReaderFullName(book.getReader().getFirstName() + " " + book.getReader().getLastName());
        } else {
            dto.setReaderFullName("Available (No reader)");
        }
        return dto;
    }
}