package com.library.api.service;

import com.library.api.dto.BookDTO;
import com.library.api.model.Book;
import java.util.List;

public interface IBookService {
    public List<Book> getBooks();
    public void saveBook(Book book);
    public void deleteBook(Long idBook);
    public Book findBook(Long idBook);
    public BookDTO getBookInfo(Long idBook); // Aquí usaremos el DTO
    public List<BookDTO> getBooksByGenre(String genre);
    public void assignBookToBranch(Long idBook, Long idBranch);
    public void borrowBook(Long idBook, Long idReader);
    public void returnBook(Long idBook);

    }