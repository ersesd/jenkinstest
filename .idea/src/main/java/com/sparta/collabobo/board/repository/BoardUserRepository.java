package com.sparta.collabobo.board.repository;

import com.sparta.collabobo.board.entity.BoardUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {
}