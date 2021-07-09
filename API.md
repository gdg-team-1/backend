# API 연동 규격서 
version 1  
swagger참고([링크](https://gdg-hackathon-1-team.uc.r.appspot.com/swagger-ui/index.html#))

## 연동 정보
* URL: https://gdg-hackathon-1-team.uc.r.appspot.com  
* 통신방식: https  
* 통신결과: status의 코드와 오류 메시지로 정상처리 여부 확인  
* content-type: application/json  
* charset: request = UTF-8, response = UTF-8  

## 연동 규격 - 알리바이
### URL
`{서버}/api/v1/alibi`

### GET
RESPONSE는 Swagger 참고
* `/api/v1/alibi`: 전체 목록 호출 
* `/api/v1/alibi/{id}`: ID에 해당하는 알리바이 하나 호출  
* `/api/v1/alibi/search?location={location}&requestUser={requestUser}&dDay={dDay}`: 요청자의 등록된 알리바이 목록 호출
location, requestUser, dDay 는 쓰고 싶은것만 파라미터 추가해서 넘겨주시면 됩니다. 파라미터가 없으면 filtering 항목이 없으므로 전체 목록이 반환됩니다.


### POST
* `/api/v1/alibi`: body의 내용인 알리바이 하나를 서버에 등록
body에 id가 있어도 상관없습니다. 무시하고 생성됩니다.


requestBody sample
```
{
  "dday": "2038-01-19 03:14:07",
  "location": "서울특별시 종로구 혜화동",
  "category": [
    "태그1",
    "태그2"
  ],
  "requestUser": "홍길동",
  "title": "ITZY 콘서트 대타가 필요해요"
}
```


### PUT
* `/api/v1/alibi/{id}`: body의 내용인 알리바이 하나를 기존 내용에서 수정
body에 id가 있어도 상관없습니다. URL에 있는 파라미터를 기준으로 수정합니다.

requestBody sample
```
{
  "dday": "2038-01-19 03:14:07",
  "location": "서울특별시 종로구 혜화동",
  "category": [
    "태그1",
    "태그2"
  ],
  "requestUser": "홍길동",
  "title": "ITZY 콘서트 대타가 필요해요"
}
```


### DELETE
* `/api/v1/alibi/{id}`: id에 해당하는 알리바이를 삭제한다.


## 연동 규격 - 유저
### URL
`{서버}/api/v1/user`

### GET
RESPONSE는 Swagger 참고
* `/api/v1/user`: 전체 목록 호출
* `/api/v1/user/detail/{id}`: ID에 해당하는 유저 정보 호출
* `/api/v1/user/search?nickname={nickname}`: ~~파라미터로 검색한 유저~~ 혹시 몰라서 구현. 사용하지 않을 예정.


### POST
* `/api/v1/user/imageFileUpload`: 파일 업로드. response로 url이 반환됨.
* `/api/v1/user`: body의 내용인 유저 하나를 서버에 등록


requestBody sample
```
{
  "id": "string",
  "nickname": "nickname",
  "profileUrl": "profileUrl"
}
```


### PUT
* `/api/v1/user/detail/{id}`: 유저 정보를 body 내용으로 수정.  
  body에 id가 있어도 상관없습니다. URL에 있는 파라미터를 기준으로 수정합니다.

requestBody sample
```
{
  "id": "string",
  "nickname": "nickname",
  "profileUrl": "profileUrl"
}
```


### DELETE
* `/api/v1/user/detail/{id}`: id에 해당하는 알리바이를 삭제한다.

