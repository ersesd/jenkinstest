package com.sparta.collabobo.worker;

import java.util.List;

public interface WorkerQuery {
    List<Worker> findWorkersInCard(Long cardId);
}
