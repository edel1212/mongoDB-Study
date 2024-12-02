# MongoDB Query Study

```properties
# ℹ️ Docker를 사용해서 실행함
#   - Mongo Compass GUI Tool을 활용하여 확인
```

## Set-Up
### MongoDB 이미지 다운로드
`docker pull mongo`

### Container 생성
```shell
docker run -d --name mongodb -v C:\Users\edel1\Desktop\docker-volume\mongo:/data/db -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=123 -p 27017:27017 mongo
```
- 정상 동작 확인 방법 아래 링크 클릭 시 확인 가능함
  - `http://localhost:{지정 포트번호}`

### Mongo DB 접속
- 컨테이너 접근
  - `docker exec -it {{containerName}} /bin/bash`
- MongoDB 접속
  - `mongosh -u root -p`

## Database

### Database 목록 확인
- `show dbs`
  - 해당 명려어 사용시 미리 만들어진 3개의 Database를 확인 할 수 있음
  - 
    ![img.png](img.png)
    
  - 각각의 설명
    - admin 
      - 관리용 데이터베이스 (권한 및 클러스터 설정). 루트 데이터베이스로 사용
      - 시스템 수준의 관리 작업(사용자 관리, 권한 설정 등)은 대부분 admin 데이터베이스에서 이루어짐
      - 사용자 인증
        - 사용자가 admin 데이터베이스에서 인증되면, MongoDB 인스턴스 전체에 대한 권한을 가질 수 있음
        - 예) root 역할을 가진 사용자를 admin 데이터베이스에 추가하면, 이 사용자는 **모든 데이터베이스를 관리**할 수 있음
      - 클러스터 관리
        - 복제본 세트(replica set), 샤드 클러스터(sharded cluster) 관리와 같은 고급 작업도 admin 데이터베이스에서 설정
      - 서버 정보 저장
        - 일부 시스템 정보와 서버 상태가 admin 데이터베이스에 저장됩니다.
    - local
      - 서버별 데이터베이스.
      - 복제본 세트 설정 및 복제되지 않는 데이터를 저장.
    - config:
      - 샤드 클러스터 환경에서 메타데이터를 저장.

### Database 생성
- `use {생성하고 싶은 데이터 베이스명}`
  - 존재하지 않는 Database를 선택해도 **에러가 나지 않음**
    - 데이터베이스 "암시적 생성" 상태이기 때문임 MongoDB는 Collection을 추가 시 진짜 Database가 생성된다.
  - "데이터베이스를 생성한다"기보다는 "선택한다"는 의미함.

### Database 삭제
> 삭제할 Database 이동
- `db.dropDatabase();'

### Database 확인
- 현재 Database 확인
	- `db`
- 내가 만든 Database List 확인
	- `show dbs`

</hr>

## 계정

### Role 종류

| 역할 (Role)      | 설명                                                          |
|-------------------|---------------------------------------------------------------|
| `read`           | 지정된 데이터베이스에 대한 읽기 권한                          |
| `readWrite`      | 지정된 데이터베이스에 대한 읽기 및 쓰기 권한                  |
| `dbAdmin`        | 데이터베이스 관리 권한 (인덱스 관리, 통계 조회 등)            |
| `userAdmin`      | 사용자 관리 권한                                              |
| `clusterAdmin`   | 클러스터 관리 권한                                           |
| `root`           | 모든 권한 (슈퍼유저)                                         |


### 계정 생성
- admin Database 선택
  - `use admin`
- 계정 생성
	- **"db"가 "admin"** 이기에 **모든 Database에 권한**을 갖는다 	
  ```javascript
  db.createUser(
  {
    user: "yoo",
    pwd:  "123",
    roles: [
    	{ "role" : "root", "db" : "admin" },
	]
  }
  ```

### 계정 삭제
> 삭제할 대상이 있는 Database 선택
- `db.dropUser("{{삭제 대상 ID}}");`

### 계정 정보 수정
> 변경할 대상이 있는 Database 선택
- ```javascript
  db.updateUser("{{대상 ID}}", {
    pwd: "{{변경할 PW}}", // 변경할 비밀번호 - optional
    roles: [ { role: "{{지정 권한}}", db: "<database_name>" } ] // 변경할 권한 - optional
  });
  ```


### 계정 목록 확인
- `db.getUsers()`



