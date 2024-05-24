package com.example.Flower.dto;


import com.example.Flower.entity.CMPost;
import com.example.Flower.entity.Users;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
public class CMPostForm {
    private Long id;
    private Users usersId; // 외래 키로 참조하는 Users 엔티티의 id
    private String title; // 글 제목
    private String content; // 글 내용
    private byte[] picture; // 게시글에 올린 사진
    private int likeCount; // 추천 개수
    private LocalDateTime regdate; // 글 작성 시간
    private int count; // 조회수

    public CMPost toEntity() {

        return new CMPost(id,usersId, title, content, picture, likeCount, regdate, count);
    }

}
