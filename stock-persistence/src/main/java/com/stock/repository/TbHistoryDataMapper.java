package com.stock.repository;

import com.stock.model.TbHistoryData;
import com.stock.model.TbHistoryDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbHistoryDataMapper {
    long countByExample(TbHistoryDataExample example);

    int deleteByExample(TbHistoryDataExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbHistoryData record);

    int insertSelective(TbHistoryData record);

    List<TbHistoryData> selectByExample(TbHistoryDataExample example);

    TbHistoryData selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbHistoryData record, @Param("example") TbHistoryDataExample example);

    int updateByExample(@Param("record") TbHistoryData record, @Param("example") TbHistoryDataExample example);

    int updateByPrimaryKeySelective(TbHistoryData record);

    int updateByPrimaryKey(TbHistoryData record);

	List<Integer> getCloseIndexsByCode(String code);
}