package com.example.Flower.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
public class CMComment {//커뮤니티 게시글 댓글 테이블
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //이 코드 때문에 더미 파일에 id값을 빼었다. 에러가 나기 때문.
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "users_id") // 외래 키로 Users 테이블의 id를 참조
    private User user; // FK(유저 정보 테이블) 유저 고유 코드

    @ManyToOne
    @JoinColumn(name = "cmPost_id") // 외래 키로 CMPost 테이블의 id를 참조
    private CMPost cmPost; // FK(유저 정보 테이블) 유저 고유 코드
    
    @Column
    private String title;//댓글 제목

    @Column(name = "content")
    private String content;

    @Column
    private int likeCount;//추천 개수

    @Column
    private LocalDateTime regdate;//일기 작성 시간
    @PrePersist
    protected void onCreate() {
        this.regdate = LocalDateTime.now();
    }
}
