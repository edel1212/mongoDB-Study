package com.yoo.single_mongo.service;

import com.yoo.single_mongo.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberService {
    Member registerMember(String accountId, Integer age);
    Member modifyMember(String accountId, Integer age);
    String deleteMember(String accountId);
    List<Member> getAllMember();
    Member findOneByAccountId(String accountId);

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


    Page<Member> getPageMembers(Pageable pageable);

}
