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
        // CORRECCIÓN: Ahora pide todos los libros al repositorio
        return bookRepo.findAll();
    }

    @Override
    public void saveBook(Book book) {
        bookRepo.save(book);
    }

    @Override
    public void deleteBook(Long idBook) {
        // CORRECCIÓN: Borra por ID usando el repositorio
        bookRepo.deleteById(idBook);
    }

    @Override
    public Book findBook(Long idBook) {
        // CORRECCIÓN: Busca en la DB. Si no lo encuentra, devuelve null
        // .orElse(null) es vital para evitar errores de Optional
        return bookRepo.findById(idBook).orElse(null);
    }

    @Override
    public BookDTO getBookInfo(Long idBook) {
        Book book = this.findBook(idBook);

        // ESCUDO DE SEGURIDAD: Si el libro no existe en MySQL, no intentamos mapearlo
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
        // 1. Buscamos los libros en la base de datos
        List<Book> books = bookRepo.findByGenre(genre);

        // 2. Usamos Streams para transformar la lista (Mapeo)
        return books.stream()
                .map(book -> {
                    BookDTO dto = new BookDTO();
                    dto.setBookTitle(book.getTitle());
                    dto.setBookGenre(book.getGenre());
                    // Lógica para el nombre del lector
                    dto.setReaderFullName(book.getReader() != null ?
                            book.getReader().getFirstName() + " " + book.getReader().getLastName() :
                            "Available in shelves");
                    return dto;
                })
                .toList(); // Convertimos el flujo de nuevo a una lista
    }


    @Override
    public void assignBookToBranch(Long idBook, Long idBranch) {
        // 1. Buscamos el libro
        Book book = this.findBook(idBook);
        if (book == null) {
            throw new AlreadyExistsException("Book not found with ID: " + idBook);
        }

        // 2. Buscamos la sede
        Branch branch = branchRepo.findById(idBranch).orElse(null);
        if (branch == null) {
            throw new AlreadyExistsException("Branch not found with ID: " + idBranch);
        }

        // 3. Realizamos la asignación
        book.setBranch(branch);

        // 4. Guardamos el cambio
        bookRepo.save(book);
    }




    @Override
    public void borrowBook(Long idBook, Long idReader) {
        // 1. Buscar el libro
        Book book = bookRepo.findById(idBook)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // 2. REGLA DE ORO: ¿Está disponible?
        if (!book.isAvailable() || book.getReader() != null) {
            throw new RuntimeException("El libro ya está prestado o no está disponible.");
        }

        // 3. Buscar al lector
        Reader reader = readerRepo.findById(idReader)
                .orElseThrow(() -> new RuntimeException("Lector no encontrado"));

        // 4. Realizar el préstamo
        book.setReader(reader);
        book.setAvailable(false); // <--- Marcamos como NO disponible

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