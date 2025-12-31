package com.library.application.usecases.impl;

import com.library.application.dto.BookResponse;
import com.library.application.dto.CreateBookRequest;
import com.library.application.exception.NotFoundException;
import com.library.application.exception.ValidationException;
import com.library.application.mapper.BookMapper;
import com.library.application.usecases.BookManagerUseCase;
import com.library.domain.model.Book;
import com.library.domain.ports.input.BookRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BookManagerUseCaseImpl implements BookManagerUseCase {
    private final BookRepositoryPort bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookResponse createBook(CreateBookRequest request){
        log.info("Creating book with ISBN: {}", request.getIsbn());

        if(bookRepository.existsByIsbn(request.getIsbn())){
            throw new ValidationException("Book with ISBN "+request.getIsbn()+" already xists");
        }

        Book book = Book.createBook(
                request.getIsbn(),
                request.getTitle(),
                request.getAuthor(),
                request.getPublicationYear(),
                request.getPublisher(),
                request.getTotalCopies()
        );

        Book savedBook = bookRepository.save(book);

        log.info("Book created successfully with id ID : {}", savedBook.getId());
        return bookMapper.toResponse(savedBook);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponse getBookById(Long id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with ID : " + id));
        return bookMapper.toResponse(book);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponse getBookByIsbn(String isbn){
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Book nou found with ISBN : "+ isbn));
        return bookMapper.toResponse(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks(){
        return bookRepository.findAll().stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> getAvailableBooks(){
        return bookRepository.findAvailableBooks().stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> searchBooks(String title, String author){
        List<Book> books;

        if(title != null && author != null){
            books = bookRepository.findByTitleContaining(title).stream()
                    .filter(
                            book -> book.getAuthor().toLowerCase().contains(author.toLowerCase())
                    )
                    .toList();
        }else if(title != null){
            books = bookRepository.findByTitleContaining(title);
        }else if(author != null){
            books = bookRepository.findByAuthor(author);
        }else{
            books = bookRepository.findAll();
        }
        return books.stream()
                .map(bookMapper::toResponse)
                .toList();
    }

    @Override
    public BookResponse updateBook(Long id, CreateBookRequest request){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with ID : "+ id));

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublicationYear(request.getPublicationYear());
        book.setPublisher(request.getPublisher());

        Book updatedBook = bookRepository.save(book);
        return bookMapper.toResponse(updatedBook);
    }

    @Override
    public void deleteBook(Long id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with ID: "+ id));

        if(!book.isAvailable()){
            throw new ValidationException("cannot delete a book that is currenly borrowed");
        }

        bookRepository.deleteById(id);
        log.info("Book deleted with ID : {}", id);
    }

    @Override
    public BookResponse addBookCopies(Long id, int additionalCopies){
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Book not found with ID : "+ id)
                );
        book.addCopies(additionalCopies);
        Book updatedBook = bookRepository.save(book);
        log.info("Added {} copies to book ID : {}", additionalCopies, id);
        return bookMapper.toResponse(updatedBook);
    }


}
