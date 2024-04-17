package com.sparta.collabobo.comment.responseDto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {

    private Long commentId;
    private String username;
    private String content;
    private LocalDateTime createdAt;

}
