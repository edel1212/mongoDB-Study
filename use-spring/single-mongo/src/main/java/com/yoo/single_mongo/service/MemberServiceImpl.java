package com.yoo.single_mongo.service;

import com.yoo.single_mongo.entity.Member;
import com.yoo.single_mongo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member registerMember(String accountId, Integer age) {
        Member member = Member.builder()
                .accountId(accountId)
                .age(age)
                .build();
        return memberRepository.save(member);
    }

    @Transactional
    @Override
    public Member modifyMember(String accountId, Integer age) {
        Member member = memberRepository.findOneByAccountId(accountId);
        if(member == null) throw new RuntimeException("not found member");
        member.setAge(age);
        return memberRepository.save(member);
    }

    @Override
    public String deleteMember(String accountId) {
        Member member = memberRepository.findOneByAccountId(accountId);
        if(member == null) throw new RuntimeException("not found member");
        memberRepository.deleteById(member.getId());
        return accountId + " :: delete success";
    }


    @Override
    public List<Member> getAllMember() {
        return memberRepository.findAll();
    }

    @Override
    public Member findOneByAccountId(String accountId) {
        return memberRepository.findOneByAccountId(accountId);
    }

    @Override
    public List<Member> findByAgeGreaterThan(int age) {
        return memberRepository.findByAgeGreaterThan(age);
    }
}
