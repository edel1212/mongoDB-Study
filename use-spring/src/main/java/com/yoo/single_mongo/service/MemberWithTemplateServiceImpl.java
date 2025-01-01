package com.yoo.single_mongo.service;

import com.yoo.single_mongo.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberWithTemplateServiceImpl implements MemberWithTemplateService{

    private final MongoTemplate mongoTemplate;

    @Override
    public Member registerMember(String accountId, Integer age) {
        Member member = Member.builder()
                .accountId(accountId)
                .age(age)
                .joinedDate(LocalDateTime.now())
                .build();
        return mongoTemplate.insert(member);
    }

    @Override
    public Member modifyMember(String accountId, Integer age) {
        Query query = new Query();
        query.addCriteria(Criteria.where("accountId").is(accountId));
        Member member = mongoTemplate.findOne(query, Member.class);
        if (member == null) throw new RuntimeException("not found member");
        member.setAge(age);
        return mongoTemplate.save(member);
    }

    @Override
    public String deleteMember(String accountId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("accountId").is( accountId));
        Member member = mongoTemplate.findOne(query, Member.class);
        if (member == null) throw new RuntimeException("not found member");
        mongoTemplate.remove(query, Member.class);
        return accountId + " delete Success";
    }

    @Override
    public List<Member> getAllMember() {
        return mongoTemplate.findAll(Member.class);
    }

    @Override
    public Member findOneByAccountId(String accountId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("accountId").is(accountId));
        return mongoTemplate.findOne(query, Member.class);
    }

    @Override
    public List<Member> findByAgeGreaterThan(int age) {
        Query query = new Query();
        query.addCriteria(Criteria.where("age").gt(age));
        return mongoTemplate.find(query, Member.class);
    }

    @Override
    public List<Member> findByAgeGreaterThanEqual(int age) {
        Query query = new Query();
        query.addCriteria(Criteria.where("age").gte(age));
        return mongoTemplate.find(query, Member.class);
    }

    @Override
    public List<Member> findByAgeLessThan(int age) {
        Query query = new Query();
        query.addCriteria(Criteria.where("age").lt(age));
        return mongoTemplate.find(query, Member.class);
    }

    @Override
    public List<Member> findByAgeLessThanEqual(int age) {
        Query query = new Query();
        query.addCriteria(Criteria.where("age").lte(age));
        return mongoTemplate.find(query, Member.class);
    }

    @Override
    public List<Member> findByJoinedDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        Query query = new Query();
        // 🎶 날짜 Between은 get, lte 를 사용해서 비교가 가능하다
        query.addCriteria(Criteria.where("joinedDate").gte(startDate).lte(endDate));
        return mongoTemplate.find(query, Member.class);
    }

    @Override
    public Page<Member> getPageMembers(Pageable pageable) {
        // Query 객체 생성
        Query query = new Query().with(pageable);

        // 전체 데이터 개수 조회
        long total = mongoTemplate.count(query, Member.class);

        // 페이지 데이터 조회
        List<Member> members = mongoTemplate.find(query, Member.class);

        // Page 객체로 반환
        return new PageImpl<>(members, pageable, total);
    }
}
