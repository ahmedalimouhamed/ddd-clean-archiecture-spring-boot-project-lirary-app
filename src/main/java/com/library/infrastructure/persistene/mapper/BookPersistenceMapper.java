package com.library.infrastructure.persistene.mapper;

import com.library.domain.model.Book;
import com.library.infrastructure.persistene.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookPersistenceMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BookEntity toEntity(Book book);

    Book toDomain(BookEntity entity);

}
