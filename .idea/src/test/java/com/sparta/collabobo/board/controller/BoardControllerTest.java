package com.sparta.collabobo.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.collabobo.board.config.TestSecurityConfig;
import com.sparta.collabobo.board.dto.requestdto.BoardRequestDto;
import com.sparta.collabobo.board.dto.responsedto.BoardResponseDto;
import com.sparta.collabobo.board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(controllers = BoardController.class,
    includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = TestSecurityConfig.class))
class BoardControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BoardService boardService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @WithMockUser(username = "testUser", authorities = {"ROLE_USER"})
  void createBoardTest() throws Exception {
    // Given
    BoardRequestDto requestDto = new BoardRequestDto();
    BoardResponseDto responseDto = new BoardResponseDto();

    given(boardService.createBoard(any(UserDetails.class), eq(requestDto))).willReturn(responseDto);

    // When, Then
    mockMvc.perform(post("/api/boards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto)))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  void updateBoardTest() throws Exception {
    // given: 준비 단계로 테스트에서 필요한 데이터와 동작을 설정한다.
    BoardRequestDto requestDto = new BoardRequestDto(/* 데이터 설정 */);
    BoardResponseDto updatedBoard = new BoardResponseDto(/* 데이터 설정 */);
    given(boardService.updateBoard(eq(1L), any(), eq(requestDto))).willReturn(updatedBoard);

    // when: 테스트를 실행하는 단계이다. 실제 액션을 설정한다.
    mockMvc.perform(put("/api/boards/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto)))
        // then: 결과를 검증하는 단계이다. 결과를 확인할 수 있다.
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  void deleteBoardTest() throws Exception {
    // given: 설정할게 없습니다.

    // when: 게시글 삭제 요청을 보낸다.
    mockMvc.perform(delete("/api/boards/{id}", 1L))
        // then: 응답 상태 코드가 200인지 확인
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  void inviteUserToBoardTest() throws Exception {
    // given: 준비 단계에 특별히 설정할게 없습니다.

    // when: 사용자를 게시글에 초대하는 요청을 보낸다.
    mockMvc.perform(post("/api/boards/{id}/invite", 1L)
            .param("email", "user@example.com"))
        // then: 응답 상태 코드가 200인지 확인
        .andExpect(status().isOk());
  }


}