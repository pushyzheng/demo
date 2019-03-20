package site.pushy.seckill.pojo;

import java.util.Date;

public class Order {

    private Integer id;

    private Integer stockId;

    private Date timestamp;

    public Order() {
    }

    public Order(Integer stockId) {
        this.stockId = stockId;
        this.timestamp = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}