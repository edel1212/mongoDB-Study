package com.yoo.single_mongo.service;

import com.yoo.single_mongo.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class MemberWithTemplateServiceImpl implements MemberWithTemplateService{

    private final MongoTemplate mongoTemplate;

    @Override
    public Member registerMember(String accountId, Integer age) {
        Member member = Member.builder()
                .accountId("use-mongo-template" + accountId)
                .age(age)
                .joinedDate(LocalDateTime.now())
                .build();
        return mongoTemplate.insert(member);
    }

    @Override
    public Member modifyMember(String accountId, Integer age) {
        Query query = new Query();
        query.addCriteria(Criteria.where("accountId").is("use-mongo-template" + accountId));
        Member member = mongoTemplate.findOne(query, Member.class);
        if (member == null) throw new RuntimeException("not found member");
        member.setAge(age);
        return mongoTemplate.save(member);
    }

    @Override
    public String deleteMember(String accountId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("accountId").is("use-mongo-template" + accountId));
        Member member = mongoTemplate.findOne(query, Member.class);
        if (member == null) throw new RuntimeException("not found member");
        mongoTemplate.remove(query, Member.class);
        return accountId + " delete Success";
    }

    @Override
    public List<Member> getAllMember() {
        return mongoTemplate.findAll(Member.class);
    }
}
