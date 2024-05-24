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
public class Diary {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //이 코드 때문에 더미 파일에 id값을 빼었다. 에러가 나기 때문.
    private Long id;//유저 보유 식물 고유 번호

    @Column(name = "USERNAME")
    private String userName;

    @Column
    private String title;

    @Column(name = "content", nullable = true)
    private String content;

    @Column(name = "picture", columnDefinition="LONGBLOB", nullable = true)
    private byte[] picture;//게시글에 올린 사진

    @Column
    private LocalDateTime regdate;//일기 작성 시간
    @PrePersist
    protected void onCreate() {
        this.regdate = LocalDateTime.now();
    }
}
