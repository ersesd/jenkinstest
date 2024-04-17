package com.sparta.collabobo.board.dto.responsedto;

import com.sparta.collabobo.board.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String description;
    private String color;
    private boolean deleted;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime deletedDate;
    private Long userId;

    public BoardResponseDto(Long id, String title, String description, String color, LocalDateTime createdDate, LocalDateTime updatedDate, Long id1) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.color = color;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.userId = id1;
    }


    // 엔티티를 받아서 BoardResponseDto를 생성함
    public static BoardResponseDto from(BoardEntity boardEntity) {
        return BoardResponseDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .description(boardEntity.getDescription())
                .color(boardEntity.getColor())
                .deleted(boardEntity.isDeleted())
                .createdDate(boardEntity.getCreatedDate())
                .updatedDate(boardEntity.getUpdatedDate())
                .deletedDate(boardEntity.getDeletedDate())
                .build();
    }

    public static BoardResponseDto from2(BoardEntity boardEntity) {
        return BoardResponseDto.builder()
            .id(boardEntity.getId())
            .title(boardEntity.getTitle())
            .description(boardEntity.getDescription())
            .color(boardEntity.getColor())
            .deleted(boardEntity.isDeleted())
            .createdDate(boardEntity.getCreatedDate())
            .updatedDate(boardEntity.getUpdatedDate())
            .deletedDate(boardEntity.getDeletedDate())
            .userId(boardEntity.getUser().getId()) // User ID 필드가 추가되었다고 가정합니다.
            .build();
    }
}