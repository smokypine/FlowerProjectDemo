package com.example.Flower.dto;


import com.example.Flower.entity.Diary;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
public class DiaryForm {
    private Long id;
    private String userName;
    private String title;
    private String content;
    private byte[] picture;
    private LocalDateTime regdate;;

    public Diary toEntity() {
        return new Diary(id, userName, title, content, picture, regdate);
    }

}
