package com.example.Flower.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class CMPost {//커뮤니티 게시글 테이블
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //이 코드 때문에 더미 파일에 id값을 빼었다. 에러가 나기 때문.
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id") // 외래 키로 Users 테이블의 id를 참조
    private User user; // FK(유저 정보 테이블) 유저 고유 코드

    @Column
    private String title;//글 제목

    @Column(name = "content", nullable = true)
    private String content;

    @Column(name = "picture", columnDefinition="LONGBLOB", nullable = true)
    private byte[] picture;//게시글에 올린 사진

    @Column(name = "like_count")
    private int likeCount;//추천 개수

    @Column
    private LocalDateTime regdate;//게시글 작성 시간

    @PrePersist
    protected void onCreate() {
        this.regdate = LocalDateTime.now();
    }

    @Column
    private int count;//조회수

}

