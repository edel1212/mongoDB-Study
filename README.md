# MongoDB Study

## 링크
- Query - [참고](https://github.com/edel1212/mongoDB-Study/tree/main/query-study)
- SpringBoot 연동
  - 단일 연동 - [참고](https://github.com/edel1212/mongoDB-Study/tree/main/use-spring)
  - Multiple 연동 - [참고](https://github.com/edel1212/springBootStudy/tree/main/dbStudy/multipleDB-mongo)

## MongoDB란?

- MongoDB는 오픈소스 NoSQL 데이터베이스로, 관계형 데이터베이스(RDBMS)와 달리 문서(Document) 지향적인 데이터 모델을 사용하는 데이터베이스다.
- 데이터를 JSON 형식과 유사한 **BSON(Binary JSON) 형태**로 저장하며, 유연하고 확장성이 뛰어난 특성을 가지고 있습니다.
  - BSON(Binary JSON) 사용 이유
    - JSON의 경우 텍스트 기반으로 구문 분석이 매우 느림
    - JSON은 공간 효율성과는 거리가 멈
  - MongoDB 내부에서는 BSON을 사용하지만, 사용자에게 보여줄 경우에는 JSON으로 변경하여 보여줌

## 주요 특징

### 스키마리스(Schema-less)
- 데이터 구조(스키마)를 **사전에 정의하지 않아됨**
- 컬렉션 내의 각 문서가 **서로 다른** 필드와 데이터 **형식을 가질 수 있음**
- **유연한** 데이터 **모델링**이 가능

### 문서(Document) 지향 저장
- 데이터를 **Key-Value 형태**로 저장
- 하나의 문서에는 **객체, 배열, 중첩 구조가 포함**될 수 있음
  - RDBMS의 여러 테이블 관계를 하나의 문서로 처리 가능함

### 수평 확장성(Sharding)
- 데이터를 여러 서버에 분산 저장(샤딩)하여 대규모 데이터를 처리 가능
  - 클러스터링 및 데이터 복제가 쉬움

### 고성능
- **읽기/쓰기 성능**이 **높고**, 대량의 데이터를 **빠르게 처리** 가능
- 대규모 애플리케이션에 적합

### 다양한 데이터 타입 지원
- 문자열, 숫자, 배열, 날짜, 이진 데이터 등 다양한 데이터 타입을 지원 함

### 강력한 쿼리 기능
- JSON 스타일의 쿼리 언어를 제공하며, 복잡한 쿼리와 데이터 분석도 가능
- 인덱싱, 집계 프레임워크(Aggregation Framework) 제공

## 주요 구성 요소

### Database
- RDBMS의 데이터베이스와 동일한 개념입니다.

### Collection
- 데이터의 그룹으로, RDBMS의 **테이블(Table)** 에 해당 함
- 각 컬렉션은 **여러 문서(Document)를 포함**하며, **동일한 스키마를 강제하지 않음**

### Document
- 기본 데이터 단위로, RDBMS의 **행(Row)** 과 비슷한 개념
- **JSON 형식과 유사한 구조**를 가지며, BSON(Binary JSON)으로 저장

### Field
- 문서 내의 키(Key)-값(Value) 쌍이며, RDBMS의 **열(Column)** 과 비슷한 개념

## RDBMS 비교

### MongoDB 사용이 더 작합한 경우

### 유연한 데이터 구조가 필요한 경우
- 스키마가 **자주 변경**되거나 **데이터 구조가 고정되지 않은** 경우
  - 예: 소셜 미디어, 사용자 맞춤형 설정 저장.
### 대규모 데이터를 처리해야 하는 경우
- 데이터가 엄청난 속도로 증가하고, 수평 확장이 필요한 경우
### 비정형 데이터 처리
- JSON이나 XML 같은 **비정형 데이터, 중첩 데이터 구조를 저장**할 때 
### 읽기/쓰기 성능이 중요한 경우
- **복잡한 JOIN 없이 데이터를 읽거나 쓰기 때문**에 대규모 트래픽을 효율적으로 처리

### RDB 사용이 더 작합한 경우

#### 트랜잭션 안정성이 중요한 경우
- 데이터의 일관성과 ACID(Atomicity, Consistency, Isolation, Durability) 특성이 중요한 경우
  - MongoDB의 경우 데이터 업데이트 중 장애 발생 시, 데이터 손실 가능성이 존재함

#### 고정된 데이터 스키마
- 데이터 구조가 고정되어 있는 경우

#### 데이터 무결성이 중요한 경우
- 외래 키(Foreign Key)나 제약 조건(Constraint)을 통해 데이터 무결성을 강하게 보장해야 하는 경우

### 비교

| **특징**            | **MongoDB**                   | **RDBMS**                     |
|---------------------|-------------------------------|--------------------------------|
| **데이터 모델**      | 문서(Document)-지향           | 테이블(Table)-지향            |
| **스키마**          | 스키마리스(Schema-less)        | 고정된 스키마                 |
| **관계**            | 비관계형(No Join)             | 관계형(Join 지원)             |
| **읽기/쓰기 속도**   | 빠름                         | 복잡한 쿼리에서 더 느림       |
| **사용 사례**       | 비정형, 대규모 데이터         | 관계형, 무결성 요구 데이터    |
| **도구 및 표준**    | JSON/BSON 기반, 신흥 도구 지원| SQL 기반, 성숙한 생태계 지원  |

  