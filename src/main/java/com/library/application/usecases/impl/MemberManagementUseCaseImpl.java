//package com.library.application.usecases.impl;
//
//import com.library.application.dto.CreateMemberRequest;
//import com.library.application.dto.MemberResponse;
//import com.library.application.exception.NotFoundException;
//import com.library.application.exception.ValidationException;
//import com.library.application.mapper.MemberMapper;
//import com.library.application.usecases.MemberManagementUseCase;
//import com.library.domain.model.Member;
//import com.library.domain.ports.input.MemberRepositoryPort;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class MemberManagementUseCaseImpl implements MemberManagementUseCase {
//    private final MemberRepositoryPort memberRepository;
//    private final MemberMapper memberMapper;
//
//    @Override
//    public MemberResponse createMember(CreateMemberRequest request){
//        log.info("Creating member with ID : {}", request.getMemberId());
//
//        if(memberRepository.existsByMemberId(request.getMemberId())){
//            throw new ValidationException("Member with ID "+request.getMemberId()+" already exists");
//        }
//
//        if(memberRepository.existsByMemberId(request.getEmail())){
//            throw new ValidationException("Member wih email "+request.getEmail()+ " already exists");
//        }
//
//        Member member = Member.create(
//                request.getMemberId(),
//                request.getFirstName(),
//                request.getLastName(),
//                request.getEmail(),
//                request.getPhone()
//        );
//
//        Member savedMember = memberRepository.save(member);
//
//        log.info("Member created successfully with ID : {}", savedMember.getId());
//        return memberMapper.toResponse(savedMember);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public MemberResponse getMemberById(Long id){
//        Member member = memberRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Member not found with ID: "+id));
//        return memberMapper.toResponse(member);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public MemberResponse getMemberByMemberId(String memberId){
//        Member member= memberRepository.findByMemberId(memberId)
//                .orElseThrow(() ->
//                    new NotFoundException("Member not found with member ID : "+memberId)
//                );
//        return memberMapper.toResponse(member);
//
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<MemberResponse> getAllMembers(){
//        return memberRepository.findAll().stream()
//                .map(memberMapper::toResponse)
//                .toList();
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<MemberResponse> getActiveMembers(){
//        return memberRepository.findActiveMembers().stream()
//                .map(memberMapper::toResponse)
//                .toList();
//    }
//
//    @Override
//    public MemberResponse updateMember(Long id, CreateMemberRequest request){
//        Member member = memberRepository.findById(id)
//                .orElseThrow(
//                        () -> new IllegalArgumentException("Membe not found with ID : "+ id)
//                );
//
//        member.setFirstName(request.getFirstName());
//        member.setLastName(request.getLastName());
//        member.setEmail(request.getEmail());
//        member.setPhone(request.getPhone());
//
//        Member updatedMember = memberRepository.save(member);
//        return memberMapper.toResponse(updatedMember);
//    }
//
//    @Override
//    public void deactivateMember(Long id){
//        Member member = memberRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Member not found with ID : " + id));
//
//        member.deactivate();
//        memberRepository.save(member);
//        log.info("Member deactivated with ID : {}", id);
//    }
//
//    @Override
//    public void renewMembership(Long id){
//        Member member = memberRepository.findById(id)
//                .orElseThrow(() ->new NotFoundException("Member no found with ID : "+ id));
//        member.renewMembership();
//        memberRepository.save(member);
//        log.info("Membership renewed for member ID: {}", id);
//    }
//
//}
