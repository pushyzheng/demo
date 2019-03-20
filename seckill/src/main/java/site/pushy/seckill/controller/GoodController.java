package site.pushy.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.pushy.seckill.dao.OrderMapper;
import site.pushy.seckill.dao.StockMapper;
import site.pushy.seckill.limit.RateLimit;
import site.pushy.seckill.limit.SemaphoreLimit;
import site.pushy.seckill.lock.DistributedLock;
import site.pushy.seckill.pojo.Order;
import site.pushy.seckill.pojo.Stock;
import site.pushy.seckill.service.GoodService;

import java.util.Random;

/**
 * @author Pushy
 * @since 2019/3/3 18:31
 */
@RestController
public class GoodController {

    @Autowired
    private GoodService goodService;

    @Autowired
    private DistributedLock distributedLock;

    /**
     * 对秒杀购买的接口限流，让同一客户端在10秒以内只能访问10次该接口
     */
    @RequestMapping("/purchase/{id}")
    @RateLimit(key = "test", time = 10, count = 10)
    public String purchaseGood(@PathVariable int id) {
        goodService.secondKillGood(id);
        return "购买成功";
    }

    @RequestMapping("/book")
    @SemaphoreLimit(key = "good", timeout = 10000, limit = 5)
    public String bookGood() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "预定成功";
    }

    @RequestMapping("/lock")
    public String lockTest() {
        String identifier = distributedLock.acquire("game");
        if (identifier == null) {
            return "获取锁失败";
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            distributedLock.release("game", identifier);
        }
        return "success";
    }

}
