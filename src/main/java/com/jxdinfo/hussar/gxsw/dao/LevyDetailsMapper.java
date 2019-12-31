package com.jxdinfo.hussar.gxsw.dao;

import com.jxdinfo.hussar.gxsw.model.LevyDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author hasee
 */
@Mapper

public interface LevyDetailsMapper {
    /**
     * 用于批量保存实缴数据
     * @param levyDetailsList 实缴数据
     * @return 保存结果
     */
    Integer insertSelective(List<LevyDetails> levyDetailsList);

}