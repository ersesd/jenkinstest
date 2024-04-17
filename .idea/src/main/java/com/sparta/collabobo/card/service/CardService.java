package com.sparta.collabobo.card.service;

import com.sparta.collabobo.board.entity.BoardEntity;
import com.sparta.collabobo.board.repository.BoardRepository;
import com.sparta.collabobo.card.entity.Card;
import com.sparta.collabobo.card.entity.CardColorEnum;
import com.sparta.collabobo.card.entity.CardStatusEnum;
import com.sparta.collabobo.card.repository.CardRepository;
import com.sparta.collabobo.card.requestDto.CardRequest;
import com.sparta.collabobo.card.requestDto.CardUpdateRequest;
import com.sparta.collabobo.card.requestDto.StatusUpdateRequest;
import com.sparta.collabobo.card.responseDto.CardResponse;
import com.sparta.collabobo.entity.User;
import com.sparta.collabobo.global.aop.Lock;
import com.sparta.collabobo.worker.WorkerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final BoardRepository boardRepository;
    private final WorkerRepository workerRepository;

    @Transactional
    public void createCard(Long boardId, CardRequest request, User user) {

        BoardEntity board = findBoard(boardId);
        // 색상 예외처리
        if (CardColorEnum.isNotExistsColor(request.getColor())) {
            throw new IllegalArgumentException("잘못된 색상이 들어왔습니다.");
        }
        //컬럼 상태 예외처리
        if (CardStatusEnum.isNotExistsStatus(request.getStatus())) {
            throw new IllegalArgumentException("잘못된 상태 컬럼이 들어왔습니다.");
        }

        Card card = new Card(
            request.getTitle(),
            request.getContent(),
            request.getColor(),
            request.getStatus(),
            user,
            board);
        cardRepository.save(card);

    }


    public CardResponse getCard(Long boardId, Long cardId, User user) {
        findBoard(boardId); //해당하는 보드의 카드를 가져와야 함
        Card card = findCard(cardId);
        boolean isNotMatchedWorker = workerRepository.findWorkersInCard(card.getId())
            .stream() //그냥 card해도 되는지
            .noneMatch(worker -> worker.getUser().getId().equals(user.getId()));
        if (isNotMatchedWorker) {
            throw new IllegalArgumentException("해당 카드의 작업자가 아닙니다.");
        }
        return cardRepository.cardQuery(cardId); // todo : boardId까지
    }
    // todo : User 가져와야 하는 이유?
    // todo : getCard로 넘어가기 전에 exception처리 해줘야하나?


    @Lock
    public void updateCard(Long cardId, CardUpdateRequest request, User user) {
        Card card = findCard(cardId);
        boolean isNotMatchedWorker = workerRepository.findWorkersInCard(card.getId()).stream()
            .noneMatch(worker -> worker.getUser().getId().equals(user.getId()));
        if (isNotMatchedWorker) {
            throw new IllegalArgumentException("해당 카드의 작업자가 아닙니다.");
        }
        card.updateCard(
            request.getTitle(),
            request.getContent());
    }

    @Lock
    public void deleteCard(Long boardId, Long cardId, User user) {
        boolean isNotMatchedWorker = workerRepository.findWorkersInCard(cardId).stream()
            .noneMatch(worker -> worker.getUser().getId().equals(user.getId()));
        if (isNotMatchedWorker) {
            throw new IllegalArgumentException("해당 카드의 작업자가 아닙니다.");
        }
        BoardEntity board = findBoard(boardId);
        Card card = findCard(cardId);
        card.softDelete(board, user);
    }

    @Lock
    public void ChangeCardStatus(Long cardId, StatusUpdateRequest request, User user) {
        Card card = findCard(cardId);
        boolean isNotMatchedWorker = workerRepository.findWorkersInCard(card.getId()).stream()
            .noneMatch(worker -> worker.getUser().getId().equals(user.getId()));
        if (isNotMatchedWorker) {
            throw new IllegalArgumentException("해당 카드의 작업자가 아닙니다.");
        }
        card.updateCardStatus(
            request.getStatus()
        );

//        Worker worker = workerRepository.findByCardIdAndUserId(cardId, user.getId())
//            .orElseThrow(() -> new IllegalArgumentException("접근할 수 있는 권한이 없습니다."));
//        if (CardAuthorityEnum.WORKER.equals(worker.getRole()) && !CardStatusEnum.isNotExistsStatus(
//            request.getStatus())) {
//            card.updateCardStatus(request.getStatus());
//        } else {
//            throw new IllegalArgumentException("잘못된 상태 컬럼이 들어왔습니다.");
//        }

    }

    private BoardEntity findBoard(Long boardId) {
        return boardRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("없는 보드입니다."));
    }

    private Card findCard(Long cardId) {
        return cardRepository.findById(cardId)
            .orElseThrow(() -> new IllegalArgumentException("없는 카드입니다."));
    }

}
