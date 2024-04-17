package com.sparta.collabobo.comment.service;

import com.sparta.collabobo.card.entity.Card;
import com.sparta.collabobo.card.repository.CardRepository;
import com.sparta.collabobo.comment.entity.Comment;
import com.sparta.collabobo.comment.repository.CommentRepository;
import com.sparta.collabobo.comment.requestDto.CommentRequest;
import com.sparta.collabobo.comment.responseDto.CommentResponse;
import com.sparta.collabobo.entity.User;
import com.sparta.collabobo.entity.UserRoleEnum;
import com.sparta.collabobo.jwt.JwtUtil;
import com.sparta.collabobo.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    public CommentService(CommentRepository commentRepository, CardRepository cardRepository, UserRepository userRepository, JwtUtil jwtUtil) {
        this.commentRepository = commentRepository;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public CommentResponse createComment(Long cardId, CommentRequest commentDto, String token) {
        try {
            logger.info("Received token: {}", token);
            // 토큰에서 공백 문자 제거
            String jwtToken = token.replace("Bearer ", "").trim();
            // 토큰 검증
            if (!jwtUtil.validateToken(jwtToken)) {
                throw new SecurityException("유효하지 않은 토큰입니다.");
            }

            // 토큰에서 사용자 정보 추출
            Claims claims = jwtUtil.getUserInfoFromToken(jwtToken);
            String username = claims.getSubject();

            UserRoleEnum role = UserRoleEnum.valueOf(
                (String) claims.get(JwtUtil.AUTHORIZATION_KEY));

            // userRepository에서 username으로 사용자 정보 가져오기
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

            // Card와 Comment 객체를 생성
            Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("카드를 찾을 수 없습니다."));

            Comment comment = new Comment(user, commentDto.getContent(), card);
            Comment savedComment = commentRepository.save(comment);

            // CommentResponse 객체 생성 및 반환
            return new CommentResponse(
                savedComment.getCommentId(),
                user.getUsername(),
                savedComment.getContent(),
                savedComment.getCreatedAt()
            );

        } catch (Exception e) {
            // 예외 발생 시 로그에 기록 및 스택 트레이스 출력
            logger.error("댓글 생성 중 예외 발생: {}", e.getMessage());
            e.printStackTrace(); // 여기에서 스택 트레이스를 출력합니다.
            throw e; // 예외를 다시 던져서 상위로 전파
        }
    }

    public List<CommentResponse> getCommentsByCardId(Long cardId) {
        List<Comment> comments = commentRepository.findByCard_Id(cardId);
        // Comment 엔티티 리스트를 CommentResponse 리스트로 변환
        return comments.stream()
            .map(comment -> new CommentResponse(
                comment.getCommentId(),
                comment.getUser().getUsername(),
                comment.getContent(),
                comment.getCreatedAt()))
            .collect(Collectors.toList());
    }

}
