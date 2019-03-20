package site.pushy.seckill.dao;

import org.apache.ibatis.annotations.Param;
import site.pushy.seckill.pojo.Stock;
import site.pushy.seckill.pojo.StockExample;

import java.util.List;

public interface StockMapper {

    long countByExample(StockExample example);

    int deleteByExample(StockExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Stock record);

    int insertSelective(Stock record);

    List<Stock> selectByExample(StockExample example);

    Stock selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Stock record, @Param("example") StockExample example);

    int updateByExample(@Param("record") Stock record, @Param("example") StockExample example);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);

    /**
     * 乐观锁更新库存
     */
    int updateByOptimistic(Stock record);

}