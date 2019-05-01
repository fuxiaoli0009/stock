package com.stock.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.stock.model.TbStock;
import com.stock.model.TbStockExample;

@Component 
@Mapper
public interface TbStockMapper {
    long countByExample(TbStockExample example);

    int deleteByExample(TbStockExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbStock record);

    int insertSelective(TbStock record);

    List<TbStock> selectByExample(TbStockExample example);

    TbStock selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbStock record, @Param("example") TbStockExample example);

    int updateByExample(@Param("record") TbStock record, @Param("example") TbStockExample example);

    int updateByPrimaryKeySelective(TbStock record);

    int updateByPrimaryKey(TbStock record);
}