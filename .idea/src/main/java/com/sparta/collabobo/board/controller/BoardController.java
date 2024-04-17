package com.sparta.collabobo.board.controller;

import com.sparta.collabobo.board.dto.requestdto.BoardRequestDto;
import com.sparta.collabobo.board.dto.responsedto.BoardResponseDto;
import com.sparta.collabobo.board.service.BoardService;
import com.sparta.collabobo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

  private final BoardService boardService;

  @PostMapping
  public ResponseEntity<BoardResponseDto> createBoard(
      @RequestBody BoardRequestDto requestDto,
      @AuthenticationPrincipal UserDetails userDetails) {
    BoardResponseDto responseDto = boardService.createBoard(userDetails, requestDto);
    return ResponseEntity.ok(responseDto);
  }

  @GetMapping
  public ResponseEntity<List<BoardResponseDto>> getAllBoards() {
    List<BoardResponseDto> boards = boardService.getAllBoards();
    return ResponseEntity.ok(boards);
  }

  @PutMapping("/{id}")
  public ResponseEntity<BoardResponseDto> updateBoard(
      @PathVariable Long id,
      @RequestBody BoardRequestDto requestDto,
      @AuthenticationPrincipal UserDetails userDetails) {
    BoardResponseDto updatedBoard = boardService.updateBoard(id, userDetails, requestDto);
    return ResponseEntity.ok(updatedBoard);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBoard(
      @PathVariable Long id,
      @AuthenticationPrincipal UserDetails userDetails) {
    boardService.deleteBoard(id, userDetails);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{id}/recover")
  public ResponseEntity<Void> recoverBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
    boardService.recoverDeletedBoard(id, userDetails);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{id}/invite")
  public ResponseEntity<Void> inviteUserToBoard(
      @PathVariable Long id,
      @RequestParam String email,
      @AuthenticationPrincipal UserDetails userDetails) {
    boardService.inviteUserToBoard(id, email, userDetails);
    return ResponseEntity.ok().build();
  }
}