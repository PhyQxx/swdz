package com.jxdinfo.hussar.gxsw.dao;

import com.jxdinfo.hussar.gxsw.model.GxswPayableInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author hasee
 */
@Mapper
public interface GxswPayableInfoMapper {

    /**
     * 大于2018冠县总统计
     */
    Map<String,Object> guanxianInfo(Map<String, Object> map);
    /**
     * 2018冠县总统计
     */
    Map<String,Object> firstguanxianInfo(Map<String, Object> map);

    /**
     * 用于获取符合条件的应缴信息总数
     * @param map 查询的条件
     * @return 符合条件的应缴信息总数
     */
    int getPayableInfoCount(Map<String, Object> map);

    /**
     * 用于获取符合条件的应缴信列表
     * @param map 查询的条件
     * @return 符合条件的应缴信息列表
     */
    List<Map<String,Object>> getPayableInfoList(Map<String, Object> map);

    /**
     * 用于镇的个数
     * @param map 查询的条件
     * @return 符合条件的应缴信息总数
     */
    int getTownPayableCount(Map<String, Object> map);

    /**
     * 用于获取该镇的村的个数
     * @param map 查询的条件
     * @return 符合条件的应缴信息总数
     */
    int getVillagePayableCount(Map<String, Object> map);
    /**
     * 用于获取2018各镇的统计
     */
    List<Map<String,Object>> get2018TownPayableList(Map<String, Object> map);

    /**
     * 用于获取大于2018各镇的统计
     */
    List<Map<String,Object>> getTownPayableList(Map<String, Object> map);
    /**
     * 用于获取各镇的村的统计
     */
    List<Map<String,Object>> getVillagePayableList(Map<String, Object> map);


    /**
     * 获取所有的乡镇/街道
     * @return 所有的乡镇/街道
     */
    List<Map<String,Object>> getPayableAllTown(Map<String,Object> map);

    /**
     * 以乡镇为单位获取应缴信息
     * @param map 包含乡镇和村信息
     * @return 应缴信息集合
     */
    List<Map<String, Object>> getPayableInfo(Map<String, Object> map);

    /**
     * 以村为单位获取应缴信息
     * @param map 包含村的详情信息
     * @return 已缴信息集合
     */
    List<Map<String, Object>> getPayableInfoByVillage(Map<String, Object> map);


}