package com.jxdinfo.hussar.gxsw.service;

import com.baomidou.mybatisplus.service.IService;
import com.jxdinfo.hussar.gxsw.model.GxswJcsjInfo;
import com.jxdinfo.hussar.util.ExcelSaxReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2019-10-17
 */
public interface IGxswJcsjInfoService extends IService<GxswJcsjInfo> {

    /**
     * 获取所有的数据
     * @param map
     * @return
     */
    Map<String, Object> getList(Map<String, Object> map);

/**
 * 查询所有的镇
 */
List<Map<String,Object>> getAllTown();

/**
 * 根据镇查询所有村
 */
List<Map<String,Object>> getAllVillage(Map<String,Object> map);

    /**
     *
     * @param file
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     * @throws ExcelSaxReader.ParseException
     * @throws InterruptedException
     */


    Map<String,Object> uploadOriginalData2(MultipartFile file) throws IOException, InvalidFormatException, ExcelSaxReader.ParseException, InterruptedException;
}
