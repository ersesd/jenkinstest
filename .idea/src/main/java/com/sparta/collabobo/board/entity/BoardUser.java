package com.sparta.collabobo.board.entity;

import com.sparta.collabobo.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "boarduser")
@NoArgsConstructor
@AllArgsConstructor
public class BoardUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @Enumerated(EnumType.STRING)
    private InvitationStatus invitationStatus;

    // 초대 상태에 대한 Enum (각각 수락, 거절, 보류상태)
    public enum InvitationStatus {
        ACCEPTED, DECLINED, PENDING
    }
}