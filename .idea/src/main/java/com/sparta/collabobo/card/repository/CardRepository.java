package com.sparta.collabobo.card.repository;

import com.sparta.collabobo.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long>, CardQuery {

}
