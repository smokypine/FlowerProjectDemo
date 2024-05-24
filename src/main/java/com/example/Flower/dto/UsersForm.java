package com.example.Flower.dto;

import com.example.Flower.entity.Users;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class UsersForm {
    private Long id;
    private String userId; // 사용자 아이디
    private String password; // 비밀번호
    private String name;
    private int age;
    private String phoneNumber;
    private String email;
    private int classes = 1;

    public Users toEntity() {

        return new Users(id,userId,password, name, age, phoneNumber, email, classes);
    }

}