package com.example.Flower.service;

import com.example.Flower.entity.CMPost;
import com.example.Flower.repository.CMPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CMPostService {

    @Autowired
    private CMPostRepository cmPostRepository;

    public void savePost(CMPost post) {
        cmPostRepository.save(post);
    }

    public List<CMPost> findAllPosts() {
        return (List<CMPost>) cmPostRepository.findAll();
    }
}
