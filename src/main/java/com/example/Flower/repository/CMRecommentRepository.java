package com.example.Flower.repository;

import com.example.Flower.entity.CMRecomment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CMRecommentRepository extends CrudRepository<CMRecomment, Long> {
    List<CMRecomment> findByCmCommentId(Long cmCommentId); // 특정 댓글의 모든 대댓글을 조회하는 메서드
}
