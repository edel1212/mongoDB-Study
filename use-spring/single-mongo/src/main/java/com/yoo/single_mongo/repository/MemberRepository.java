package com.yoo.single_mongo.repository;

import com.yoo.single_mongo.entity.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MemberRepository extends MongoRepository<Member, String> {
    // 사용자 정의 쿼리 메서드
    Member findOneByAccountId(String accountId);
    List<Member> findByAgeGreaterThan(int age);
}
