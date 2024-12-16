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
    
    /**
     * 입력 받은 accountID 조회
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

    /**
     * 페이징
     * */
    Page<Member> getPageMembers(Pageable pageable);

}
