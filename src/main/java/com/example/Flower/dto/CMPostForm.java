package com.example.Flower.dto;

import com.example.Flower.entity.CMPost;
import com.example.Flower.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CMPostForm {
    private Long id;
    private Users usersId; // 외래 키로 참조하는 Users 엔티티의 id
    private String title; // 글 제목
    private String content; // 글 내용
    private MultipartFile picture; // 게시글에 올린 사진
    private String pictureBase64; // Base64 인코딩된 사진
    private int likeCount; // 추천 개수
    private LocalDateTime regdate; // 글 작성 시간
    private int count; // 조회수

    public CMPostForm(Long id, Users usersId, String title, String content, String pictureBase64, int likeCount, LocalDateTime regdate, int count) {
        this.id = id;
        this.usersId = usersId;
        this.title = title;
        this.content = content;
        this.pictureBase64 = pictureBase64;
        this.likeCount = likeCount;
        this.regdate = regdate;
        this.count = count;
    }

    public CMPost toEntity(byte[] pictureBytes) {
        return new CMPost(id, usersId, title, content, pictureBytes, likeCount, regdate, count);
    }
}
