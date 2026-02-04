package com.library.api.service;

import com.library.api.dto.BookDTO;
import com.library.api.model.Book;
import java.util.List;

public interface IBookService {
    public List<BookDTO> getBooks();
    public void saveBook(Book book);
    public void deleteBook(Long idBook);
    public BookDTO findBook(Long idBook);
    public BookDTO getBookInfo(Long idBook);
    public List<BookDTO> getBooksByGenre(String genre);
    public void assignBookToBranch(Long idBook, Long idBranch);
    public void borrowBook(Long idBook, Long idReader);
    public void returnBook(Long idBook);
}