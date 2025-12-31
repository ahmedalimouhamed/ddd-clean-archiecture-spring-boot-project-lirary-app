package com.library.infrastructure.persistene.adapter;

import com.library.domain.model.Book;
import com.library.domain.model.BookStatus;
import com.library.domain.ports.input.BookRepositoryPort;
import com.library.infrastructure.persistene.entity.BookEntity;
import com.library.infrastructure.persistene.mapper.BookPersistenceMapper;
import com.library.infrastructure.persistene.repository.BookJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookRepositoryAdapter implements BookRepositoryPort {
    private final BookJpaRepository jpaRepository;
    private final BookPersistenceMapper mapper;

    @Override
    public Book save(Book book){
        BookEntity entity = mapper.toEntity(book);
        BookEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Book> findById(Long id){
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn){
        return jpaRepository.findByIsbn(isbn)
                .map(mapper::toDomain);
    }

    @Override
    public List<Book> findAll(){
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Book> findByAuthor(String author){
        return jpaRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Book> findByTitleContaining(String title){
        return jpaRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Book> findAvailableBooks(){
        return jpaRepository.findByStatusAndAvailableCopiesGreaterThan(
                BookStatus.AVAILABLE,
                0
        ).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id){
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByIsbn(String isbn){
        return jpaRepository.existsByIsbn(isbn);
    }


}
