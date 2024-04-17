package com.sparta.collabobo.card.repository;

import com.sparta.collabobo.card.entity.Card;
import com.sparta.collabobo.card.responseDto.CardResponse;
import java.util.List;

public interface CardQuery{
    CardResponse cardQuery(Long cardId);
}
