package com.sparta.collabobo.card.responseDto;

import com.sparta.collabobo.comment.responseDto.CommentResponse;
import java.util.List;
import lombok.Getter;

@Getter
public class CardResponse {
    private Long cardId;
    private String title;
    private String content;
    private List<CommentResponse> comments;

    public CardResponse(Long cardId, String title, String content, List<CommentResponse> comments) {
        this.cardId = cardId;
        this.title = title;
        this.content = content;
        this.comments = comments;
    }
}
