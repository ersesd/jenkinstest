package com.sparta.collabobo.card.controller;

import com.sparta.collabobo.card.requestDto.CardRequest;
import com.sparta.collabobo.card.requestDto.CardUpdateRequest;
import com.sparta.collabobo.card.requestDto.StatusUpdateRequest;
import com.sparta.collabobo.card.responseDto.CardResponse;
import com.sparta.collabobo.card.service.CardService;
import com.sparta.collabobo.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CardController {

    private final CardService cardService;

    // 카드 생성
    @PostMapping("/boards/{boardId}/cards")
    public void createCard(
        @PathVariable Long boardId,
        @Valid @RequestBody CardRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        cardService.createCard(boardId, request, userDetails.getUser());
    }

    // 카드 조회
    @GetMapping("/boards/{boardId}/cards/{cardId}")
    public CardResponse getCard(
        @PathVariable Long boardId,
        @PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return cardService.getCard(boardId, cardId, userDetails.getUser());
    }

    // 카드 수정
    @PutMapping("/cards/{cardId}")
    public void updateCard(
        @PathVariable Long cardId,
        @Valid @RequestBody CardUpdateRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        cardService.updateCard(cardId, request, userDetails.getUser());
    }

    // 카드 삭제
    @DeleteMapping("/boards/{boardId}/cards/{cardId}")
    public void deleteCard(
        @PathVariable Long boardId,
        @PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
        ) {
        cardService.deleteCard(boardId, cardId, userDetails.getUser());
    }

    // 카드 상태 변경
    @PatchMapping("/cards/{carId}/status")
    public void ChangeCardStatus(
        @PathVariable Long carId,
        @Valid @RequestBody StatusUpdateRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        cardService.ChangeCardStatus(carId, request, userDetails.getUser());
    }


}
