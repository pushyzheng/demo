package org.example.demo.sharding.dao;

import org.example.demo.sharding.dao.tables.TOrder;
import org.example.demo.sharding.model.dto.OrderDTO;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao extends BaseDao{

    private static final TOrder TABLE = Tables.T_ORDER;

    public boolean saveOrder(OrderDTO orderDTO) {
        return getShardingDslCtx()
                .insertInto(TABLE)
                .set(TABLE.ORDER_NUM, orderDTO.getOrderNum())
                .set(TABLE.USER_ID, orderDTO.getUserId())
                .set(TABLE.CHANNEL, orderDTO.getChannel())
                .execute() > 0;
    }

    public OrderDTO getByOrderNum(long orderNum) {
        Result<Record> fetch = getShardingDslCtx()
                .select()
                .from(TABLE)
                .where(TABLE.ORDER_NUM.equal(orderNum))
                .fetch();
        if (fetch.isEmpty()) {
            return null;
        }
        Record record = fetch.get(0);
        return new OrderDTO()
                .setId(record.get(TABLE.ID))
                .setOrderNum(record.get(TABLE.ORDER_NUM))
                .setUserId(record.get(TABLE.USER_ID))
                .setChannel(record.get(TABLE.CHANNEL));
    }
}
