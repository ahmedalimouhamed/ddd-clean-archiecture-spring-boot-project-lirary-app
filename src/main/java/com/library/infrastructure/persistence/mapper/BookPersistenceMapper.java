package com.library.infrastructure.persistence.mapper;

import com.library.domain.model.Book;
import com.library.infrastructure.persistence.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface BookPersistenceMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", expression = "java(book.getStatus())")
    BookEntity toEntity(Book book);

    @Mapping(target = "status", expression = "java(entity.getStatus())")
    Book toDomain(BookEntity entity);

}
