package com.yoo.single_mongo.service;

import com.yoo.single_mongo.entity.Member;

import java.util.List;

public interface MemberService {
    Member registerMember(String accountId, Integer age);
    Member modifyMember(String accountId, Integer age);
    String deleteMember(String accountId);
    List<Member> getAllMember();
    Member findOneByAccountId(String accountId);
    List<Member> findByAgeGreaterThan(int age);
}
