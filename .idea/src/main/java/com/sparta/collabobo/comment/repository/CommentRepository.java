package com.sparta.collabobo.comment.repository;

import com.sparta.collabobo.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 카드 ID에 해당하는 모든 댓글을 찾는 메소드
    List<Comment> findByCard_Id(Long cardId);


}
