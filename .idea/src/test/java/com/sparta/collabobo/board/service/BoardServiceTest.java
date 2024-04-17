package com.sparta.collabobo.board.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

import com.sparta.collabobo.board.config.TestConfig;
import com.sparta.collabobo.board.dto.requestdto.BoardRequestDto;
import com.sparta.collabobo.board.dto.responsedto.BoardResponseDto;
import com.sparta.collabobo.board.entity.BoardEntity;
import com.sparta.collabobo.board.repository.BoardRepository;
import com.sparta.collabobo.board.repository.BoardUserRepository;
import com.sparta.collabobo.entity.User;
import com.sparta.collabobo.user.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BoardService.class, TestConfig.class})
public class BoardServiceTest {

    @MockBean
    private BoardRepository boardRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BoardUserRepository boardUserRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserDetails userDetails;

    @Autowired
    private User user;

    @BeforeEach
    void setUp() {
        // Setup mock behavior
        Mockito.when(userRepository.findByNickname(anyString())).thenReturn(Optional.of(user));
    }

    @Test
    void createBoard_Successful() {
        // Given
        BoardRequestDto requestDto = new BoardRequestDto();
        requestDto.setTitle("Test Board");
        requestDto.setDescription("Description");
        requestDto.setColor("Color");

        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setTitle(requestDto.getTitle());

        Mockito.when(boardRepository.save(Mockito.any(BoardEntity.class))).thenReturn(boardEntity);

        // When
        BoardResponseDto result = boardService.createBoard(userDetails, requestDto);

        // Then
        assertEquals("Test Board", result.getTitle());
    }

    @Test
    void getAllBoards_Successful() {
        // Given
        User mockUser = new User(); // 테스트를 위해서 가짜 User 객체 생성한다.
        mockUser.setId(1L); // User 객체에 필요한 속성을 설정한다.
        mockUser.setNickname("TestUser");

        BoardEntity boardEntity1 = new BoardEntity();
        boardEntity1.setId(1L);
        boardEntity1.setTitle("Test Board 1");
        boardEntity1.setDescription("Description 1");
        boardEntity1.setColor("#FF0000");
        boardEntity1.setUser(mockUser);

        BoardEntity boardEntity2 = new BoardEntity();
        boardEntity2.setId(2L);
        boardEntity2.setTitle("Test Board 2");
        boardEntity2.setDescription("Description 2");
        boardEntity2.setColor("#FF0000");
        boardEntity2.setUser(mockUser);

        Mockito.when(boardRepository.findAll()).thenReturn(Arrays.asList(boardEntity1, boardEntity2));

        // When
        List<BoardResponseDto> result = boardService.getAllBoards();

        // Then
        assertEquals(2, result.size());
    }

}

