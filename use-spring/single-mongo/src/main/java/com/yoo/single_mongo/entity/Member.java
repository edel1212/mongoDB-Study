package com.yoo.single_mongo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@Document("member")
public class Member {
    // MongoDB의 DefaultId
    @Id
    private String id;
    private String accountId;
    private int age;
}
