package com.example.Flower.api;

import com.example.Flower.dto.CMPostForm;
import com.example.Flower.entity.CMPost;
import com.example.Flower.service.CMPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class CMPostApiController {

    @Autowired
    private CMPostService cmPostService;

    @PostMapping("/new")
    public ResponseEntity<CMPost> createPost(@ModelAttribute CMPostForm form) {
        MultipartFile picture = form.getPicture();
        byte[] pictureBytes = null;
        if (picture != null && !picture.isEmpty()) {
            try {
                pictureBytes = picture.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
                // 적절한 에러 처리를 추가하세요
                return ResponseEntity.status(500).build();
            }
        }
        CMPost post = form.toEntity(pictureBytes);
        cmPostService.savePost(post);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<List<CMPostForm>> listPosts() {
        List<CMPost> posts = cmPostService.findAllPosts();
        List<CMPostForm> postForms = posts.stream().map(post -> {
            String pictureBase64 = post.getPicture() != null ? Base64.getEncoder().encodeToString(post.getPicture()) : null;
            return new CMPostForm(
                    post.getId(),
                    post.getUser(),
                    post.getTitle(),
                    post.getContent(),
                    pictureBase64,
                    post.getLikeCount(),
                    post.getRegdate(),
                    post.getCount()
            );
        }).collect(Collectors.toList());
        return ResponseEntity.ok(postForms);
    }
}
