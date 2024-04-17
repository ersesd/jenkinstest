package com.sparta.collabobo.comment.controller;


import com.sparta.collabobo.comment.entity.Comment;
import com.sparta.collabobo.comment.requestDto.CommentRequest;
import com.sparta.collabobo.comment.responseDto.CommentResponse;
import com.sparta.collabobo.comment.service.CommentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/cards")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{cardId}/comments")
    public CommentResponse createComment(@PathVariable Long cardId,
                                 @RequestBody CommentRequest commentDto,
                                 @RequestHeader(value = "Authorization") String token) {
        return commentService.createComment(cardId, commentDto, token);
    }

    @GetMapping("/{cardId}/comments")
    public List<CommentResponse> getCommentsByCardId(@PathVariable Long cardId) {
        return commentService.getCommentsByCardId(cardId);
    }
}
