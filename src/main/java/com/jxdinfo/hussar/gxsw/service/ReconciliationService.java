package com.jxdinfo.hussar.gxsw.service;


import com.jxdinfo.hussar.gxsw.model.GxswPayableInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author hasee
 */
public interface ReconciliationService {
    /**
     * 用于获取征收子目
     * @return 征收子目集合
     */
    List<Map<String,Object>> getSpecificItem();

    /**
     * 用于查询对比结果异常数据列表
     * @param param 查询参数
     * @return 对比结果异常数据列表
     */
    Map<String,Object> getCompareResultList(Map<String,Object> param);

    /**
     * 用于查询补缴人员的信息列表
     * @param backckPaymentMap
     * @return
     */
    Map<String,Object> getBackPaymentList(Map<String,Object> backckPaymentMap);


    /**
     * 上传缴费数据
     */
    Map<String,Object> uploadPaymentData(MultipartFile file,String year);

    /**
     * 上传记账数据
     */
    Map<String,Object> uploadAccountingData(MultipartFile file,String year);



    void exportExceptionData(HttpServletResponse response, HttpServletRequest request,Map<String,Object> map);

    Map<String,Object> exportPaymentData(HttpServletResponse response, HttpServletRequest request,Map<String,Object> resultMap);

    /**
     * 用于将对账的异常数据保存
     * @param map 对账年份
     * @return 异常数据条数
     */
    Map<String,Object> saveErrorData(Map<String,Object> map);
}
