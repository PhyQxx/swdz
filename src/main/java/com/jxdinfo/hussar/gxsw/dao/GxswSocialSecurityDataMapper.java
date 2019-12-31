package com.jxdinfo.hussar.gxsw.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jxdinfo.hussar.gxsw.model.GxswSocialSecurityData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-10-14
 */
@Mapper
public interface GxswSocialSecurityDataMapper extends BaseMapper<GxswSocialSecurityData> {
    /**
     * 批量插入数据
     */
    Integer batchInsertAll(List<GxswSocialSecurityData> gxswSocialSecurityData);
}
