package com.yoo.single_mongo.repository;

import com.yoo.single_mongo.entity.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberRepository extends MongoRepository<Member, String> {
    /**
     * Query Method
     * */
    Member findOneByAccountId(String accountId);

    /**
     * 입력 받은 Age "초과"인 목록
     * */
    List<Member> findByAgeGreaterThan(int age);

    /**
     * 입력 받은 Age "이상"인 목록
     * */
    List<Member> findByAgeGreaterThanEqual(int age);

    /**
     * 입력 받은 Age "미만"인 목록
     * */
    List<Member> findByAgeLessThan(int age);

    /**
     * 입력 받은 Age "이하"인 목록
     * */
    List<Member> findByAgeLessThanEqual(int age);

    /**
     * 입력 받은 날짜 사이의 값
     * */
    List<Member> findByJoinedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
