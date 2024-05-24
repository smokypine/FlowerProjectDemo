MySql 연동 상태. 다만 이 점을 지켜줄 것.

application.properties 파일에서 

spring.datasource.url=jdbc:mysql://localhost:3306/firstproject_db
spring.datasource.username=root <-------------- !!!
spring.datasource.password=drkim4926^ <-------- !!!!

이 두 부분을 본인 컴퓨터의 MySql에 맞는 걸로 변환시키고 MySql에서

drop schema if exists firstproject_db;
create schema firstproject_db;
use firstproject_db;

이 코드를 FlowerApplication.java를 실행시키기 이전에 한 번 꼭 실행해 data를 리프레시 시킬 것. 그렇지 않으면 에러가 남.
