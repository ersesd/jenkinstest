package com.sparta.collabobo.board.service;

import com.sparta.collabobo.board.dto.requestdto.BoardRequestDto;
import com.sparta.collabobo.board.dto.responsedto.BoardResponseDto;
import com.sparta.collabobo.board.entity.BoardEntity;
import com.sparta.collabobo.board.entity.BoardUser;
import com.sparta.collabobo.board.entity.BoardUser.InvitationStatus;
import com.sparta.collabobo.board.repository.BoardRepository;
import com.sparta.collabobo.board.repository.BoardUserRepository;
import com.sparta.collabobo.entity.User;
import com.sparta.collabobo.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

  private final BoardRepository boardRepository;
  private final UserRepository userRepository;
  private final BoardUserRepository boardUserRepository;

  @Transactional
  public BoardResponseDto createBoard(UserDetails userDetails, BoardRequestDto requestDto) {
    User user = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

    // 보드 이름 중복 체크
    boolean isDuplicateName = boardRepository.existsByTitle(requestDto.getTitle());
    if (isDuplicateName) {
      throw new IllegalArgumentException("이미 존재하는 보드 이름입니다.");
    }

    if (requestDto.getTitle().length() < 3 || requestDto.getDescription().length() < 3) {
      throw new IllegalArgumentException("제목의 길이가 3글자 보다 짧습니다.");
    }

    BoardEntity boardEntity = BoardEntity.builder()
        .user(user) // 연관관계 설정
        .title(requestDto.getTitle())
        .description(requestDto.getDescription())
        .color(requestDto.getColor())
        .createdDate(LocalDateTime.now())
        .updatedDate(LocalDateTime.now())
        .build();

    boardRepository.save(boardEntity);
    return new BoardResponseDto(boardEntity.getId(), boardEntity.getTitle(),
        boardEntity.getDescription(), boardEntity.getColor(),
        boardEntity.getCreatedDate(), boardEntity.getUpdatedDate(), user.getId());
  }

  public List<BoardResponseDto> getAllBoards() {
    List<BoardEntity> boardEntities = boardRepository.findAll();
    return boardEntities.stream().map(board -> new BoardResponseDto(
        board.getId(),
        board.getTitle(),
        board.getDescription(),
        board.getColor(),
        board.getCreatedDate(),
        board.getUpdatedDate(),
        board.getUser().getId())
    ).collect(Collectors.toList());
  }

  @Transactional
  public BoardResponseDto updateBoard(Long boardId, UserDetails userDetails, BoardRequestDto requestDto) {
    User user = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    BoardEntity boardEntity = boardRepository.findById(boardId)
        .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

    if (!boardEntity.getUser().getId().equals(user.getId())) {
      throw new IllegalArgumentException("수정 권한이 없습니다.");
    }

    boardEntity.setTitle(requestDto.getTitle());
    boardEntity.setDescription(requestDto.getDescription());
    boardEntity.setColor(requestDto.getColor());
    boardEntity.setUpdatedDate(LocalDateTime.now());
    boardRepository.save(boardEntity);

    return new BoardResponseDto(boardEntity.getId(), boardEntity.getTitle(),
        boardEntity.getDescription(), boardEntity.getColor(),
        boardEntity.getCreatedDate(), boardEntity.getUpdatedDate(), user.getId());
  }

  @Transactional
  public void deleteBoard(Long boardId, UserDetails userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    BoardEntity boardEntity = boardRepository.findById(boardId)
        .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

    if (!boardEntity.getUser().getId().equals(user.getId())) {
      throw new IllegalArgumentException("삭제 권한이 없습니다.");
    }

    boardEntity.setDeleted(true);
    boardEntity.setDeletedDate(LocalDateTime.now());
    boardRepository.save(boardEntity);
  }

  @Transactional
  public void recoverDeletedBoard(Long boardId, UserDetails userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    BoardEntity boardEntity = boardRepository.findByIdIgnoringSoftDelete(boardId)
        .orElseThrow(() -> new IllegalArgumentException("삭제한 보드를 찾을 수 없습니다."));

    if (!boardEntity.getUser().getId().equals(user.getId())) {
      throw new IllegalArgumentException("복구 권한이 없습니다.");
    }

    if (!boardEntity.isDeleted()) {
      throw new IllegalArgumentException("이미 복구된 보드입니다.");
    }

    boardEntity.setDeleted(false);
    boardEntity.setDeletedDate(null);
    boardRepository.save(boardEntity);
  }
  @Transactional
  public void inviteUserToBoard(Long boardId, String email, UserDetails userDetails) {
    // 초대하는 사용자
    User invitingUser = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new IllegalArgumentException("초대하는 사용자를 찾을 수 없습니다."));

    BoardEntity board = boardRepository.findById(boardId)
        .orElseThrow(() -> new IllegalArgumentException("보드를 찾을 수 없습니다."));

    // 초대받을 사용자
    User userToInvite = userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("초대할 사용자를 찾을 수 없습니다."));

    // 초대 권한이 있는지 확인
    if (!board.getUser().equals(invitingUser)) {
      throw new IllegalArgumentException("사용자를 초대할 권한이 없습니다.");
    }

    // 사용자가 이미 초대되었는지 확인
    boolean alreadyInvited = board.getCollaborators().stream()
        .anyMatch(bu -> bu.getUser().equals(userToInvite));
    if (alreadyInvited) {
      throw new IllegalArgumentException("사용자가 이미 보드에 초대되었습니다.");
    }

    BoardUser boardUser = new BoardUser();
    boardUser.setBoard(board);
    boardUser.setUser(userToInvite);
    // 초대 상태를 대기 중으로 설정 (이 기능을 구현하기로 결정한 경우 enum 또는 유사한 것을 사용하는 것을 가정)
    boardUser.setInvitationStatus(InvitationStatus.PENDING);
    boardUserRepository.save(boardUser);

  }

}