package com.example.Flower.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"cmComment", "user"}) // cmComment, users 필드를 toString()에서 제외하여 순환 참조를 피함
@Entity
public class CMRecomment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 대댓글 고유번호

    @ManyToOne
    @JoinColumn(name = "cmComment_id")
    private CMComment cmComment; // 댓글 정보 가져오기

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 유저 정보 가져오기

    @Column
    private String content; // 대댓글 내용

    @Column
    private int likeCount; // 대댓글 좋아요

    @Column
    private LocalDateTime regdate; // 대댓글 작성 시간

    // 좋아요 수 증가 메서드
    public void incrementLikeCount() {
        this.likeCount++;
    }

    @PrePersist
    protected void onCreate() {
        this.regdate = LocalDateTime.now(); // 현재 시간을 regdate에 설정
    }
}
