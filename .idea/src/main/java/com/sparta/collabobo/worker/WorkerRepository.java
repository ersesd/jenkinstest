package com.sparta.collabobo.worker;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long>, WorkerQuery{
    Optional<Worker> findByCardIdAndUserId(Long cardId, Long userId);


}
