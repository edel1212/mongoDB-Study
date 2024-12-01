# MongoDB Study

## MongoDB란?

- MongoDB는 오픈소스 NoSQL 데이터베이스로, 관계형 데이터베이스(RDBMS)와 달리 문서(Document) 지향적인 데이터 모델을 사용하는 데이터베이스다.
- 데이터를 JSON 형식과 유사한 **BSON(Binary JSON) 형태**로 저장하며, 유연하고 확장성이 뛰어난 특성을 가지고 있습니다.
  - BSON(Binary JSON) 사용 이유
    - JSON의 경우 텍스트 기반으로 구문 분석이 매우 느림
    - JSON은 공간 효율성과는 거리가 멈
  - MongoDB 내부에서는 BSON을 사용하지만, 사용자에게 보여줄 경우에는 JSON으로 본경하여 보여줌