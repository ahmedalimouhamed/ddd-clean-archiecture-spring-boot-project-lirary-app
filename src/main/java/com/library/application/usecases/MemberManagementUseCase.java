package com.library.application.usecases;

import com.library.application.dto.CreateMemberRequest;
import com.library.application.dto.MemberResponse;

import java.util.List;

public interface MemberManagementUseCase {
    MemberResponse createMember(CreateMemberRequest request);
    MemberResponse getMemberById(Long memberId);
    MemberResponse getMemberByMemberId(String memberId);
    List<MemberResponse> getAllMembers();
    List<MemberResponse> getActiveMembers();
    MemberResponse updateMember(Long id, CreateMemberRequest request);
    void deactivateMember(Long id);
    void renewMembership(Long id);
}
