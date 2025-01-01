# SpringBoot - MongoDB

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

## Query Log 설정
- yml 설정을 통해 쿼리 로그 추출이 가능하다.
```yaml
logging:
  level:
    org.springframework.data.mongodb.core.MongoTemplate: DEBUG
```

## MongoRepository 
```properties
# ℹ️ Repository패턴을 기반으로한 "인터페이스"이다. 기본적인 CRUD 메서드를 자동으로 제공
#   - Spring JPA와 굉장히 유사하게 작동하므로 간단한 데이터 처리에 유용함
```

### 장/단 점 
- 장점
  - 메서드 이름만으로 커스텀 쿼리를 작성 가능
    - (ex. findByCategory(String Category))
  - 코드가 매우 간결하고 유지보수가 쉬움
- 단점
  - 복잡한 쿼리 작업에서는 제한적
  - 커스터마이징이 어렵고 고급 기능을 구현하기에 한계가 있음

### 사용 방법
- Interface 생성 후 `extends MongoRepository<T, ID> `를 **상속** 해주면 된다.
- Repository
  - ```java
    import com.yoo.single_mongo.entity.Member;
    import org.springframework.data.mongodb.repository.MongoRepository;
  
    import java.time.LocalDateTime;
    import java.util.List;
  
    public interface MemberRepository extends MongoRepository<Member, String> {
        /**
         * 입력받은 AccountId 조회
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
    ```
  - Service
    - ```java
      @Service
      @RequiredArgsConstructor
      public class MemberServiceImpl implements MemberService {
        private final MemberRepository memberRepository;
      
        // Use memberRepository method ~
      
        @Override
        public Page<Member> getPageMembers(Pageable pageable) {
            return memberRepository.findAll(pageable);
        }
      }
      ```

## MongoTemplate

```properties
# ℹ️ MongoTemplate은 복잡한 쿼리나 고유 기능이 필요한 경우에 적합한 방식
#    ㄴ MongoTemplate을 의존성 주입하여 사용 가능함
```

### 장/단 점
- 장점
  - 복잡한 커스텀 쿼리를 지원
    - QueryDsl 과 **형식이 크게 다르지 않음**
    - MongoDB의 고유 기능을 더 세밀하게 사용 가능 
  - MongoRepository와 같은 **인터페이스 정의가 필요하지 않음**
- 단점
  - MongoTemplate API에 대한 깊은 이해가 필요
  - 단순 CRUD 작업에는 오히려 복잡하고, 많은 코드 작성을 요구

### 사용 방법
- MongoTemplate 의존성 주입 수 해당 API 사용

- ServiceImpl
  ```java
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
  ```
  