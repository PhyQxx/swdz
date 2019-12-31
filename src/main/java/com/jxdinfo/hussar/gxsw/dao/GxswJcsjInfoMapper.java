package com.jxdinfo.hussar.gxsw.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jxdinfo.hussar.gxsw.model.GxswJcsjInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-10-17
 */
@Mapper
public interface GxswJcsjInfoMapper extends BaseMapper<GxswJcsjInfo> {

    Integer  getCount(Map<String, Object> map);

    /**
     * 获取查询到的数据
     * @param map
     * @return
     */
    List<Map<String, Object>> getList(Map<String, Object> map);

    /**
     * 导入基础数据
     * @param gxswJcsjInfos
     * @return
     */

    Integer batchInsert(List<GxswJcsjInfo> gxswJcsjInfos);
    /**
     * 获取所有的镇
     */
    List<Map<String,Object>> getAllTown();
    /**
     * 根据乡镇查询所有的村
     */
    List<Map<String,Object>> getAllVillage(Map<String,Object> map);
}
