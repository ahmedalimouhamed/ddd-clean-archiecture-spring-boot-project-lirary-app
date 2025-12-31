package com.library.infrastructure.persistene.repository;

import com.library.domain.model.BookStatus;
import com.library.infrastructure.persistene.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {
    Optional<BookEntity> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
    List<BookEntity> findAll();
    List<BookEntity> findByAuthorContainingIgnoreCase(String auhor);
    List<BookEntity> findByTitleContainingIgnoreCase(String title);
    List<BookEntity> findByStatusAndAvailableCopiesGreaterThan(
            BookStatus status,
            Integer availableCopies
    );
}
