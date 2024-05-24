package com.example.Flower.dto;


import com.example.Flower.entity.CMComment;
import com.example.Flower.entity.CMPost;
import com.example.Flower.entity.Users;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
public class CMCommentForm {
    private Long id;
    private Users usersId; // 외래 키로 참조하는 Users 엔티티의 id
    private CMPost cmPostId; // 외래 키로 참조하는 CMPost 엔티티의 id
    private String title; // 글 제목
    private String content; // 글 내용
    private int likeCount; // 추천 개수
    private LocalDateTime regdate; // 글 작성 시간

    public CMComment toEntity() {

        return new CMComment(id,usersId,cmPostId, title, content, likeCount, regdate);
    }

}
