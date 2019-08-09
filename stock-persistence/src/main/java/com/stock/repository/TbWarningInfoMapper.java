package com.stock.repository;

import com.stock.model.TbWarningInfo;
import com.stock.model.TbWarningInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbWarningInfoMapper {
    long countByExample(TbWarningInfoExample example);

    int deleteByExample(TbWarningInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbWarningInfo record);

    int insertSelective(TbWarningInfo record);

    List<TbWarningInfo> selectByExample(TbWarningInfoExample example);

    TbWarningInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbWarningInfo record, @Param("example") TbWarningInfoExample example);

    int updateByExample(@Param("record") TbWarningInfo record, @Param("example") TbWarningInfoExample example);

    int updateByPrimaryKeySelective(TbWarningInfo record);

    int updateByPrimaryKey(TbWarningInfo record);
}