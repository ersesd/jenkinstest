package com.sparta.collabobo.card.repository;

import static com.sparta.collabobo.card.entity.QCard.card;
import static com.sparta.collabobo.comment.entity.QComment.comment;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.collabobo.card.entity.Card;
import com.sparta.collabobo.card.responseDto.CardResponse;
import com.sparta.collabobo.comment.responseDto.CommentResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CardQueryImpl implements CardQuery {

    private final JPAQueryFactory queryFactory;

    @Override
    public CardResponse cardQuery(Long cardId) {
        Card getCard = queryFactory
            .selectFrom(card)
            .where(card.id.eq(cardId))
            .fetchOne();

        List<CommentResponse> getComments = queryFactory
            .select(
                Projections.constructor(CommentResponse.class,
                    comment.commentId,
                    comment.content,
                    comment.createdAt))
            .from(comment)
            .where(comment.card.id.eq(cardId))
            .fetch();

        return new CardResponse(getCard.getId(), getCard.getTitle(), getCard.getContent(),
            getComments);
    }
}
