package com.yoo.single_mongo.service;

import com.yoo.single_mongo.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberWithTemplateServiceImplTest {

    @Autowired
    private MemberWithTemplateService memberWithTemplateService;

    @DisplayName("계정 등록")
    @Test
    void registerMember() {
        Member member = memberWithTemplateService.registerMember("zero", 23);
        System.out.println("-----------------------------");
        System.out.println(member);
        System.out.println("-----------------------------");
    }

    @Test
    void modifyMember() {
        Member member = memberWithTemplateService.modifyMember("zero", 50050);
        System.out.println("-----------------------------");
        System.out.println(member);
        System.out.println("-----------------------------");
    }

    @Test
    void deleteMember() {
    }

    @Test
    void getAllMember() {
    }
}