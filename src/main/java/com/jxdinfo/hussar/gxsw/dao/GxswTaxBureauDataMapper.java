package com.jxdinfo.hussar.gxsw.dao;

import com.jxdinfo.hussar.gxsw.model.GxswSocialSecurityData;
import com.jxdinfo.hussar.gxsw.model.GxswTaxBureauData;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *上传记账数据
 * @author 
 * @since 2019-10-14
 */
@Mapper
public interface GxswTaxBureauDataMapper extends BaseMapper<GxswTaxBureauData> {
    /**
     * 批量插入数据
     */
    Integer batchInsertAll(List<GxswTaxBureauData> list);

    /**
     * 清空表内容
     */
    Integer deleteSocialData(String year);

    /**
     * 清空gxsw_tax_bureau_data数据表
     */
    Integer deleteTaxData(String year);
    /**
     * 用于获取符合条件的对比结果总数
     * @param map 查询的条件
     * @return 符合条件的对比结果信息总数
     */
    int getCompareResultCount(Map<String,Object> map);

    /**
     * 用于获取符合条件的对比结果列表
     * @param map 查询的条件
     * @return 符合条件的对比结果
     */
    List<Map<String,Object>> getCompareResultList(Map<String,Object> map);

    int getBackPaymentCount(Map<String,Object> map);

    /**
     * 获取补缴人员列表
     * @param map
     * @return
     */
    List<Map<String,Object>> getBackPaymentList(Map<String,Object> map);

    List<Map<String,Object>> getSpecificItem();

    /**
     * 用于将对账的异常数据保存
     * @param map 对账年份
     * @return 异常数据条数
     */
    int saveErrorData(Map<String,Object> map);

    /**
     * 用于删除异常数据
     * @param map 年份
     * @return 删除的条数
     */
    int deleteErrorData(Map<String,Object> map);
}
