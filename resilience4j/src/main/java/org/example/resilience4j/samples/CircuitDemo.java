package org.example.resilience4j.samples;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.vavr.control.Try;
import org.example.resilience4j.utils.Resilience4jUtils;

public class CircuitDemo {

    static long userId = 1;
    static long orderNum = 1;

    static StockService stockService = new StockService();
    static RewardPointService rewardPointService = new RewardPointService();

    public static void main(String[] args) {
        CircuitBreaker circuitBreaker =
                Resilience4jUtils.createCircuitBreaker(CircuitBreakerConfig.ofDefaults());
        // 减库存
        stockService.reduce(orderNum);

        Try.run(circuitBreaker.decorateCheckedRunnable(() -> {
            rewardPointService.increasePoint(userId);
        })).onFailure(throwable -> {
            addIncreasePointRecord(userId);
        });
    }

    private static void addIncreasePointRecord(long userId) {
    }

    private static class StockService {
        public void reduce(long orderNum) {
        }
    }

    private static class RewardPointService {
        public void increasePoint(long userId) {
        }
    }
}
