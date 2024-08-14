package com.example.Flower.repository;

import com.example.Flower.entity.CMComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CMCommentRepository extends CrudRepository<CMComment, Long> {
    List<CMComment> findByCmPostId(Long cmPostId); // 특정 게시글의 모든 댓글을 조회하는 메서드
}