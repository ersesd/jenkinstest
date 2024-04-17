package com.sparta.collabobo.global.aop;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * @Lock 선언 시 수행되는 Aop class
 */

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class RedisLock {

    private final RedissonClient redissonClient;

    @Around("@annotation(Lock)")
    public Object redissonLock(ProceedingJoinPoint joinPoint, Lock Lock) throws Throwable {
        RLock lock = redissonClient.getFairLock(Lock.value());

        if (lock.tryLock(Lock.waitTime(), Lock.leaseTime(), Lock.timeUnit())) {
            try {
                log.info("Lock");
                return joinPoint.proceed();
            } catch (InterruptedException e) {
                log.info("에러 발생");
                throw e;
            } finally {
                try {
                    lock.unlock();
                    log.info("Unlock");
                } catch (IllegalMonitorStateException e) {
                    log.info("Redisson Lock이 이미 unlock되었습니다.");
                }
            }
        } else {
            throw new RuntimeException("Failed to acquire lock");
        }
    }
}
