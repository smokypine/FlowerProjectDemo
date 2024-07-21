drop schema if exists flower;
create schema flower;
use flower;
CREATE TABLE IF NOT EXISTS Users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    USERID VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    age INT,
    PHONENUMBER VARCHAR(255),
    email VARCHAR(255),
    classes INT NOT NULL DEFAULT 1
);


-- users 테이블에 데이터를 삽입합니다.
-- userId와 phoneNumber가 각각 USERID와 PHONENUMBER로 스프링 부트가 인지를 하는 문제가 발생하여 user 엔티티에 각각 rename을 해줬음.
INSERT INTO Users (USERID, password, name, age, PHONENUMBER, email, classes) VALUES ('admin', '1234', '김동현', 31, '010-56561541', 'hayasdan@naver.com', 0);
