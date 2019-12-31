package com.jxdinfo.hussar.gxsw.service;

import com.jxdinfo.hussar.gxsw.model.GxswPayableInfo;
import com.jxdinfo.hussar.gxsw.model.LevyDetails;
import com.jxdinfo.hussar.util.ExcelSaxReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author hasee
 */
public interface GxswPayableInfoService {
    /**
     * 用于保存应缴人信息
     * @param payableInfos 应缴信息
     * @return 保存结果
     */
    Map<String,Object> savePayableInfo(List<GxswPayableInfo> payableInfos);

    /**
     * 用于获取应缴信息列表
     * @param map 查询参数
     * @return 应缴信息列表
     */
    Map<String,Object> getPayableInfoList(Map<String,Object> map);

    /**
     * 用于获取各镇的统计
     */
    Map<String,Object> getTownPayableList(Map<String,Object> map);
    /**
     * 用于获取各村的统计
     */
    Map<String,Object> getVillagePayableList(Map<String,Object> map);

    /**
     * 导出该村详情
     */
    String exportVillageFile(HttpServletResponse response, HttpServletRequest request, Map<String, Object> condition);

    /**
     * 导入实缴数据
     * @return
     */
    Map<String,Object> uploadDetailData(MultipartFile file,String year) throws IOException, InvalidFormatException, ExcelSaxReader.ParseException, InterruptedException;

    /**
     * 导出统计数据
     */
    String exportStatistics(HttpServletResponse response, HttpServletRequest request,Map<String, Object> condition);

        /**
         * 按镇或村为单位导出应缴人员信息
         */
    String exportFile(HttpServletResponse response, HttpServletRequest request,Map<String, Object> condition);

    /**
     * 冠县总统计
     */
    Map<String,Object> guanxianInfo(Map<String, Object> map);


}
