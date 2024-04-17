package com.sparta.collabobo.card.entity;

import com.sparta.collabobo.board.entity.BoardEntity;
import com.sparta.collabobo.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CardColorEnum color;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CardStatusEnum status;

    @Column(nullable = false)
    private String is_deleted;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime modified_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private BoardEntity board;


    public Card(String title, String content, String color, String status, User user,
        BoardEntity board) {
        this.title = title;
        this.content = content;
        this.color = CardColorEnum.valueOf(color);
        this.status = CardStatusEnum.valueOf(status);
        this.is_deleted = "NO";
        this.created_at = LocalDateTime.now();
        this.user = user;
        this.board = board;
    }


    public void softDelete(BoardEntity board, User user) {
        this.board = board;
        this.user = user;
        this.is_deleted = "YES";
    }

    public void updateCardStatus(String status) {
        this.status = CardStatusEnum.valueOf(status);
        this.modified_at = LocalDateTime.now();
    }

    public void updateCard(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
