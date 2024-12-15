package com.yoo.single_mongo.service;

import com.yoo.single_mongo.entity.Member;
import com.yoo.single_mongo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;

    @Override
    public Member registerMember(String accountId, Integer age) {
        Member member = Member.builder()
                .accountId(accountId)
                .age(age)
                .joinedDate(LocalDateTime.now())
                .build();
        return memberRepository.save(member);
    }

    @Transactional
    @Override
    public Member modifyMember(String accountId, Integer age) {
        Member member = memberRepository.findOneByAccountId(accountId);
        if (member == null) throw new RuntimeException("not found member");
        member.setAge(age);
        return memberRepository.save(member);
    }

    @Override
    public String deleteMember(String accountId) {
        Member member = memberRepository.findOneByAccountId(accountId);
        if (member == null) throw new RuntimeException("not found member");
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

    @Override
    public List<Member> findByAgeGreaterThanEqual(int age) {
        return memberRepository.findByAgeGreaterThanEqual(age);
    }

    @Override
    public List<Member> findByAgeLessThan(int age) {
        return memberRepository.findByAgeLessThan(age);
    }

    @Override
    public List<Member> findByAgeLessThanEqual(int age) {
        return memberRepository.findByAgeLessThanEqual(age);
    }

    @Override
    public List<Member> findByJoinedDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return memberRepository.findByJoinedDateBetween(startDate,endDate);
    }
}
