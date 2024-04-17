package com.sparta.collabobo.worker;

import static com.sparta.collabobo.worker.QWorker.worker;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WorkerQueryImpl implements WorkerQuery {

    private final JPAQueryFactory queryFactory;

    public List<Worker> findWorkersInCard(Long cardId) {
        return queryFactory
            .selectFrom(worker)
            .where(worker.card.id.eq(cardId))
            .fetch();
    }
}
