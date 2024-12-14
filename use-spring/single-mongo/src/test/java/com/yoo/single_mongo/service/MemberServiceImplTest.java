package com.yoo.single_mongo.service;

import com.yoo.single_mongo.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        memberService.registerMember("randomAccountId",randomAge);
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

}