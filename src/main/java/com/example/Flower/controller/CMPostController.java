package com.example.Flower.controller;

import com.example.Flower.dto.CMPostForm;
import com.example.Flower.entity.CMPost;
import com.example.Flower.service.CMPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
public class CMPostController extends SessionCheckController{

    @Autowired
    private CMPostService cmPostService;

    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("postForm", new CMPostForm());
        return "postForm";
    }

    @PostMapping("/new")
    public String createPost(CMPostForm form) {
        MultipartFile picture = form.getPicture();
        byte[] pictureBytes = null;
        if (picture != null && !picture.isEmpty()) {
            try {
                pictureBytes = picture.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
                // 적절한 에러 처리를 추가하세요
            }
        }
        CMPost post = form.toEntity(pictureBytes);
        cmPostService.savePost(post);
        return "redirect:/posts";
    }

    @GetMapping
    public String listPosts(Model model) {
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
        model.addAttribute("posts", postForms);
        return "postList";
    }
}
