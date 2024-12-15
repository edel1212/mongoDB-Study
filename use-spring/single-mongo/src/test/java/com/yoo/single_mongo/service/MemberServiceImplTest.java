package com.yoo.single_mongo.service;

import com.yoo.single_mongo.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    @DisplayName("register Member")
    @Test
    void registerMember() throws Exception{
        int randomAge = new Random().nextInt(100) + 1;
        String randomAccountId = UUID.randomUUID().toString();
        memberService.registerMember(randomAccountId,randomAge);
    }

    @DisplayName("modify Member")
    @Test
    void modifyMember() throws Exception{
        memberService.modifyMember("edel1212",111);
    }

    @Test
    void deleteMember() throws Exception{
        memberService.registerMember("zero", 3);
        memberService.deleteMember("zero");
    }

    @DisplayName("all Member")
    @Test
    void findAllMember() throws Exception{
        memberService.getAllMember()
                .forEach(System.out::println);
    }

    @DisplayName("accountId List")
    @Test
    void findByAccountId() throws Exception{
        Member member = memberService.findOneByAccountId("edel1212");
        System.out.println("------------------------------");
        System.out.println(member);
        System.out.println("------------------------------");
    }

    @DisplayName("지정 나이 초과 목록 조회")
    @Test
    void findByAgeGreaterThan() throws Exception{
        List<Member> memberList = memberService.findByAgeGreaterThan(555);
        System.out.println("------------------------------");
        System.out.println(memberList);
        System.out.println("------------------------------");
    }

    @DisplayName("지정 나이 이상 목록 조회")
    @Test
    void findByAgeGreaterThanEqual() throws Exception {
        List<Member> memberList = memberService.findByAgeGreaterThanEqual(50);
        System.out.println("------------------------------");
        memberList.forEach(System.out::println);
        System.out.println("------------------------------");
    }

    @DisplayName("지정 나이 미만 목록 조회")
    @Test
    void findByAgeLessThan() throws Exception {
        List<Member> memberList = memberService.findByAgeLessThan(32);
        System.out.println("------------------------------");
        memberList.forEach(System.out::println);
        System.out.println("------------------------------");
    }

    @DisplayName("지정 나이 이하 목록 조회")
    @Test
    void findByAgeLessThanEqual() throws Exception {
        List<Member> memberList = memberService.findByAgeLessThanEqual(32);
        System.out.println("------------------------------");
        memberList.forEach(System.out::println);
        System.out.println("------------------------------");
    }

    @DisplayName("가입 날짜 범위 조회")
    @Test
    void findByJoinedDateBetween() throws Exception {
        // 테스트를 위한 날짜 범위 설정
        LocalDateTime startDate = LocalDateTime.now().minusDays(10); // 10일 전
        LocalDateTime endDate = LocalDateTime.now(); // 현재 날짜

        List<Member> memberList = memberService.findByJoinedDateBetween(startDate, endDate);
        System.out.println("------------------------------");
        memberList.forEach(System.out::println);
        System.out.println("------------------------------");
    }

    @DisplayName("페이징 조회")
    @Test
    void getPageList() throws Exception {
        int page = 0 ;
        int listSize = 10;
        PageRequest pageable = PageRequest.of(page, listSize);
        Page<Member> memberList = memberService.getPageMembers(pageable);
        System.out.println("------------------------------");
        System.out.println("총 Row 수: " + memberList.getTotalElements());
        System.out.println("총 페이지 수: " + memberList.getTotalPages());
        System.out.println("현재 페이지 번호: " + memberList.getNumber());
        System.out.println("페이지 당 Row 수: " + memberList.getSize());
        memberList.forEach(System.out::println);
        System.out.println("------------------------------");
    }
}