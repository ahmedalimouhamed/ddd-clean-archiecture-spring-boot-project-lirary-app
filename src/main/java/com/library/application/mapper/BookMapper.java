package com.library.application.mapper;

import ch.qos.logback.core.model.ComponentModel;
import com.library.application.dto.BookResponse;
import com.library.domain.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "status", expression="java(book.getStatus().name())")
    @Mapping(target = "active", source = "active")
    BookResponse toResponse(Book book);
}
