package com.library.domain.ports.input;

import com.library.domain.model.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryPort {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByMemberId(String memberId);
    Optional<Member> findEmail(String email);
    List<Member> findAll();
    List<Member> findActiveMembers();
    boolean existsByMemberId(String memberId);
    boolean existsByEmail(String email);
}
