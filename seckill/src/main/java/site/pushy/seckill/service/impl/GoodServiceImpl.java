package site.pushy.seckill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.pushy.seckill.dao.OrderMapper;
import site.pushy.seckill.dao.StockMapper;
import site.pushy.seckill.pojo.Order;
import site.pushy.seckill.pojo.Stock;
import site.pushy.seckill.service.GoodService;

/**
 * @author Pushy
 * @since 2019/3/3 21:08
 */
@Service
public class GoodServiceImpl implements GoodService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StockMapper stockMapper;

    @Override
    public void secondKillGood(int id) {
        Stock stock = checkStock(id);
        if (stock == null) {
            throw new RuntimeException("秒杀商品不存在");
        }
        stock.sale();
        //int res = stockMapper.updateByPrimaryKey(stock);
        int res = stockMapper.updateByOptimistic(stock);
        if (res == 0) {
            throw new RuntimeException("秒杀失败");
        }
        createOrder(stock.getId());
    }

    private Stock checkStock(int id) {
        Stock stock = stockMapper.selectByPrimaryKey(id);
        if (stock.getSale().equals(stock.getCount())) {
            throw new RuntimeException("库存不足");
        }
        return stock;
    }

    private void createOrder(int stockId) {
        orderMapper.insert(new Order(stockId));
    }

}
