package com.library.application.mapper;

import com.library.application.dto.MemberResponse;
import com.library.domain.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "sping")
public interface MemberMapper {
    @Mapping(target = "maxBorrowedBooks", constant = "5")
    MemberResponse toResponse(Member member);
}
