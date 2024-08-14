package com.example.Flower.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="UserMember")//User은 키워드로 사용되기에 엔티티명으로 사용은 불가능하다. 하지만 @Table(name="")을 사용하면 UserMember로 테이블명이 만들어지지만 User.java가 사용이 가능.
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String name;
    private int age;
    private String phonenumber;
    private String email;
    private UserRole role;
    private int active; //회원 활성화, 비활성화. 1이면 활성화. 0이면 비활성화.

}

