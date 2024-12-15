# SpringBoot - MongoDB 단일 연동

## Dependencies 설정

```groovy
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-mongodb'
}
```

## Application 설정

```properties
# ℹ️ 모든 Database에 권한을 갖는 계정을 사용할 경우 "authSource=admin" 설정 필요
#    ㄴ use admin을 통해 생성한 계정일 경우
```
- url 설정 값  - **[ 필수 ]**
  - `mongodb://<계정ID>:<비밀번호>@<도메인 정보>/<사용 데이터베이스>?authSource=admin`
- auto-index-creation 설정 값 - **[ Optional ]** 
  - Java의 Entity 내 Document 필드 값에 대한 설정을 할 경우 해당 설정 필요
    - ex) unique 필드 설정 
```yaml
spring:
  data:
    mongodb:
      # ⭐️ 관리자 권한을 갖는 계정일 경우 "?authSource=admin" 설정 필수
      uri: mongodb://admin:123@localhost:27017/book?authSource=admin
      # Spring Data MongoDB가 애플리케이션 시작 시 자동으로 인덱스를 생성하도록 할 수 있음
      auto-index-creation: true
```

## Entity 생성
- ✨ **[ 필수 ]** 
  - `@Document("<대상 Collection명(Table명)>")`을 지정해줘야 한다
  - `@Id`어노테이션이 달리는 `private String id;`는 필수다
    - MongoDB 내 `_id`로 식별 Key로 사용함
    - 값을 지정해서 넣을 수 있음 - **중복X** 
- `@Indexed(unique = true)` 사용 시 중복 값 등록 방지 가능
```java
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

    // 고유값 성정
    @Indexed(unique = true)
    private String accountId;

    @Setter
    private int age;

    @Setter
    private LocalDateTime joinedDate;
}
```

## 