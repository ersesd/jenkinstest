package com.sparta.collabobo.board.repository;

import com.sparta.collabobo.board.entity.BoardEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

  boolean existsByTitle(String title);

  @Query(value = "SELECT * FROM board WHERE id = :id", nativeQuery = true)
  Optional<BoardEntity> findByIdIgnoringSoftDelete(@Param("id") Long id);
}
