package com.jxdinfo.hussar.gxsw.service.impl;

import com.jxdinfo.hussar.gxsw.dao.GxswPayableInfoMapper;
import com.jxdinfo.hussar.gxsw.dao.LevyDetailsMapper;
import com.jxdinfo.hussar.gxsw.model.GxswPayableInfo;
import com.jxdinfo.hussar.gxsw.model.LevyDetails;
import com.jxdinfo.hussar.gxsw.service.GxswPayableInfoService;
import com.jxdinfo.hussar.util.DownLoadUtils;
import com.jxdinfo.hussar.util.ExcelSaxReader;
import com.jxdinfo.hussar.util.bigdataexcel.BaseThreadPool;
import com.jxdinfo.hussar.util.bigdataexcel.Callback;
import com.jxdinfo.hussar.util.bigdataexcel.ExcelUtil;
import com.jxdinfo.hussar.util.bigdataexcel.SimpleThreadPool;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author hasee
 */
@Service
public class GxswPayableInfoServiceImpl implements GxswPayableInfoService {

    @Autowired
    private GxswPayableInfoMapper payableInfoMapper;

    @Autowired
    private LevyDetailsMapper levyDetailsMapper;

    @Autowired
    private ExcelSaxReader excelSaxReader;

    @Value("${hussar.download-path}")
    private String dirPath;

    /**
     * 用于保存应缴信息
     * @param payableInfos 应缴信息
     * @return 保存结果
     */
    @Override
    public Map<String, Object> savePayableInfo(List<GxswPayableInfo> payableInfos) {
        Map<String, Object> result = new HashMap<>(5);
        int count = 0;
        result.put("count", count);
        return result;
    }

    /**
     * 冠县总统计
     */
    @Override
    public Map<String, Object> guanxianInfo(Map<String, Object> con) {
        Map<String, Object> map = null;
        if (Integer.parseInt((String) con.get("year"))>2018) {
            map = payableInfoMapper.guanxianInfo(con);
        } else {
            map = payableInfoMapper.firstguanxianInfo(con);
        }

        return map;
    }

    /**
     * 用于查询各镇的统计
     */
    @Override
    public Map<String, Object> getTownPayableList(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>(5);
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        int pageNumber = Integer.parseInt((String) map.get("pageNumber"));
        int startNumber = (pageNumber - 1) * pageSize;
        map.put("startNumber", startNumber);
        map.put("pageSize", pageSize);
        int total = payableInfoMapper.getTownPayableCount(map);
        List<Map<String, Object>> payableInfoList;
        if (Integer.parseInt((String) map.get("year"))>2018) {
            payableInfoList = payableInfoMapper.getTownPayableList(map);
        }else {
            payableInfoList = payableInfoMapper.get2018TownPayableList(map);
        }
        result.put("total", total);
        result.put("rows", payableInfoList);
        return result;
    }

    /**
     * 用于查询各村的统计
     *
     * @param map 查询参数
     * @return 符合条件的应缴信息列表
     */
    @Override
    public Map<String, Object> getVillagePayableList(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>(5);
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        int pageNumber = Integer.parseInt((String) map.get("pageNumber"));
        int startNumber = (pageNumber - 1) * pageSize;
        map.put("startNumber", startNumber);
        map.put("pageSize", pageSize);
        int total = payableInfoMapper.getVillagePayableCount(map);
        List<Map<String, Object>> payableInfoList = payableInfoMapper.getVillagePayableList(map);
        result.put("total", total);
        result.put("rows", payableInfoList);
        return result;
    }

    /**
     * 用于查询已缴信息列表
     */
    @Override
    public Map<String, Object> getPayableInfoList(Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>(5);

        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        int pageNumber = Integer.parseInt((String) map.get("pageNumber"));
        int startNumber = (pageNumber - 1) * pageSize;

        map.put("startNumber", startNumber);
        map.put("pageSize", pageSize);
        int total = payableInfoMapper.getPayableInfoCount(map);
        List<Map<String, Object>> payableInfoList = payableInfoMapper.getPayableInfoList(map);
        result.put("total", total);
        result.put("rows", payableInfoList);
        return result;
    }

    /**
     * 导出该村详情
     */
    @Override
    public String exportVillageFile(HttpServletResponse response, HttpServletRequest request, Map<String, Object> condition) {
        String result = "";
        long startTime = System.currentTimeMillis();
        HSSFWorkbook wb = new HSSFWorkbook();
        List<Map<String, Object>> gxswPayableInfoList = payableInfoMapper.getPayableInfoByVillage(condition);
        String sheetName = condition.get("town") + "" + condition.get("village")+"社保人员清单";
        HSSFSheet sheet = wb.createSheet(sheetName);

        /* 设置打印格式 */
        HSSFPrintSetup hps = sheet.getPrintSetup();
        hps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
        hps.setLandscape(true);
        hps.setFitHeight((short) 1);
        hps.setFitWidth((short) 1);
        hps.setScale((short) 65);
        hps.setFooterMargin(0);
        hps.setHeaderMargin(0);
        sheet.setMargin(HSSFSheet.TopMargin, 0.3);
        sheet.setMargin(HSSFSheet.BottomMargin, 0);
        sheet.setMargin(HSSFSheet.LeftMargin, 0.3);
        sheet.setMargin(HSSFSheet.RightMargin, 0);


        //创建第一行
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell;
        row.setHeightInPoints(40);

        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        //粗体显示
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        cell = row.createCell(0);
        cell.setCellValue("身份证件号码");
        cell = row.createCell(1);
        cell.setCellValue("姓名");
        cell = row.createCell(2);
        cell.setCellValue("征收子目");
        cell = row.createCell(3);
        cell.setCellValue("实缴金额");
        cell = row.createCell(4);
        cell.setCellValue("街道乡镇");
        cell = row.createCell(5);
        cell.setCellValue("社区/村组");
        sheet.setColumnWidth(0, 4096);
        sheet.setColumnWidth(1, 4096);
        sheet.setColumnWidth(2, 4096);
        sheet.setColumnWidth(3, 4096);
        sheet.setColumnWidth(4, 4096);
        //设置列值-内容
        for (int j = 0; j < gxswPayableInfoList.size(); j++) {
            row = sheet.createRow(j + 1);
            row.setHeightInPoints(20);
            cell = row.createCell(0);
            cell.setCellValue((String) gxswPayableInfoList.get(j).get("credit_code"));
            cell = row.createCell(1);
            cell.setCellValue((String) gxswPayableInfoList.get(j).get("taxpayer_name"));
            cell = row.createCell(2);
            cell.setCellValue((String) gxswPayableInfoList.get(j).get("specific_item"));
            cell = row.createCell(3);
            cell.setCellValue((String) gxswPayableInfoList.get(j).get("payment_amount"));
            cell = row.createCell(4);
            cell.setCellValue((String) gxswPayableInfoList.get(j).get("town"));
            cell = row.createCell(5);
            cell.setCellValue((String) gxswPayableInfoList.get(j).get("village"));
        }
        //获得浏览器代理信息
        String finalFileName = null;
        try {
            finalFileName = DownLoadUtils.getFileName(request.getHeader("USER-AGENT"), sheetName+".xls");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //设置HTTP响应头
        response.reset();        //重置 响应头
        response.setContentType("application/vnd.ms-excel");//告知浏览器下载文件，而不是直接打开，浏览器默认为打开
        response.addHeader("Content-Disposition", "attachment;filename=" + finalFileName + "");//下载文件的名称

        //将数据写入输出流
        OutputStream os = null;
        try {
            os = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        //导出总耗时
        long time = endTime - startTime;
        return result;
    }

    /**
     * 导入实缴表
     * @param
     * @return
     */
    @Override
    public Map<String, Object> uploadDetailData(MultipartFile file, String year) throws IOException, InvalidFormatException, ExcelSaxReader.ParseException, InterruptedException {
        Map<String, Object> map = new HashMap<>();
        Integer count = 0;
        long startTime = System.currentTimeMillis();
        InputStream inputStream=null;
        try{
            List<LevyDetails> levyDetails = new ArrayList<>();
            //转换为输入流
            inputStream = file.getInputStream();
            ExcelSaxReader reader = excelSaxReader.parse(inputStream);
            List<String[]> datas = reader.getDatas();
            long endTime = System.currentTimeMillis();
            //读取Excel耗时            =============45s左右
            long time = endTime-startTime;
            for(String[] str : datas){
                LevyDetails levyDetail = new LevyDetails();
                levyDetail.setCreditCode(str[0]);
                levyDetail.setTaxpayerName(str[1]);
                levyDetail.setTaxItem(str[2]);
                levyDetail.setPaymentAmount(str[3]);
                levyDetail.setStartDate(str[4]);
                levyDetail.setEndDate(str[5]);
                levyDetail.setSpecificItem(str[6]);
                levyDetail.setYear(year);
                levyDetails.add(levyDetail);
            }
            long startTime2 = System.currentTimeMillis();
            //插入数据库
            count = levyDetailsMapper.insertSelective(levyDetails);
            int size = levyDetails.size();
            int index = 0;
            while(true) {
                if(index+3000>=size) {
                    levyDetailsMapper.insertSelective(levyDetails.subList(index, size-1));
                    break;
                }else {
                    levyDetailsMapper.insertSelective(levyDetails.subList(index, index+3000));
                    index = index+3000;
                }

            }
            count = size;
            long endTime2 = System.currentTimeMillis();
            //执行插入耗时             =============57s左右
            long time2 = endTime2-startTime2;
            long allTime = time+time2;
            map.put("time", allTime);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        map.put("count", count);
        return map;
    }

    /**
     * 导出统计数据
     */
    @Override
    public String exportStatistics(HttpServletResponse response, HttpServletRequest request, Map<String, Object> condition) {
        String result = "";


            //冠县统计
            Map<String, Object> guanxianMap = null;
            guanxianMap = payableInfoMapper.guanxianInfo(condition);

            //各镇统计信息
            List<Map<String, Object>> allTownMap = null;
            allTownMap = payableInfoMapper.getTownPayableList(condition);

            HSSFWorkbook wb = new HSSFWorkbook();
        {
            HSSFSheet sheet = wb.createSheet("冠县各镇已缴统计数据");
            /* 设置打印格式 */
            HSSFPrintSetup hps = sheet.getPrintSetup();
            hps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
            hps.setLandscape(true);
            hps.setFitHeight((short) 1);
            hps.setFitWidth((short) 1);
            hps.setScale((short) 65);
            hps.setFooterMargin(0);
            hps.setHeaderMargin(0);
            sheet.setMargin(HSSFSheet.TopMargin, 0.3);
            sheet.setMargin(HSSFSheet.BottomMargin, 0);
            sheet.setMargin(HSSFSheet.LeftMargin, 0.3);
            sheet.setMargin(HSSFSheet.RightMargin, 0);


            //创建第一行
            HSSFRow row = sheet.createRow((short) 0);
            HSSFCell cell;
            row.setHeightInPoints(40);

            HSSFFont font = wb.createFont();
            font.setFontName("宋体");
            //粗体显示
            font.setBold(true);
            font.setFontHeightInPoints((short) 16);
            cell = row.createCell(0);
            cell.setCellValue("街道乡镇");
            cell = row.createCell(1);
            cell.setCellValue("总金额(元)");
            cell = row.createCell(2);
            cell.setCellValue("去年总金额(元)");
            cell = row.createCell(3);
            cell.setCellValue("总人数(人)");
            cell = row.createCell(4);
            cell.setCellValue("去年总人数(人)");
            sheet.setColumnWidth(0, 4096);
            sheet.setColumnWidth(1, 4096);
            sheet.setColumnWidth(2, 4096);
            sheet.setColumnWidth(3, 4096);
            sheet.setColumnWidth(4, 4096);
            //设置列值-内容
            for (int j = 0; j < allTownMap.size(); j++) {
                row = sheet.createRow(j + 1);
                row.setHeightInPoints(20);
                cell = row.createCell(0);
                cell.setCellValue((String) allTownMap.get(j).get("town"));
                cell = row.createCell(1);
                cell.setCellValue(String.valueOf(Math.round((Double) allTownMap.get(j).get("allmoney"))));
                cell = row.createCell(2);
                cell.setCellValue(String.valueOf(Math.round((Double) allTownMap.get(j).get("lastyearallmoney"))));
                cell = row.createCell(3);
                cell.setCellValue(String.valueOf(allTownMap.get(j).get("allpeople")));
                cell = row.createCell(4);
                cell.setCellValue(String.valueOf(allTownMap.get(j).get("lastyearallpeople")));
            }
            row = sheet.createRow(allTownMap.size() + 1);
            row.setHeightInPoints(20);
            cell = row.createCell(0);
            cell.setCellValue("冠县总合计");
            cell = row.createCell(1);
            cell.setCellValue(String.valueOf(Math.round((Double) guanxianMap.get("allmoney"))));
            cell = row.createCell(2);
            cell.setCellValue(String.valueOf(Math.round((Double) guanxianMap.get("lastyearallmoney"))));
            cell = row.createCell(3);
            cell.setCellValue(String.valueOf(guanxianMap.get("allpeople")));
            cell = row.createCell(4);
            cell.setCellValue(String.valueOf(guanxianMap.get("lastyearallpeople")));

        }
        {
            for (int n = 0; n < allTownMap.size(); n++) {

                condition.put("town", allTownMap.get(n).get("town"));
                //该镇各村的统计数据
                List<Map<String, Object>> villageMap = payableInfoMapper.getVillagePayableList(condition);
                HSSFSheet sheet = wb.createSheet("冠县" + allTownMap.get(n).get("town") + "已缴统计数据");

                /* 设置打印格式 */
                HSSFPrintSetup hps = sheet.getPrintSetup();
                hps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
                hps.setLandscape(true);
                hps.setFitHeight((short) 1);
                hps.setFitWidth((short) 1);
                hps.setScale((short) 65);
                hps.setFooterMargin(0);
                hps.setHeaderMargin(0);
                sheet.setMargin(HSSFSheet.TopMargin, 0.3);
                sheet.setMargin(HSSFSheet.BottomMargin, 0);
                sheet.setMargin(HSSFSheet.LeftMargin, 0.3);
                sheet.setMargin(HSSFSheet.RightMargin, 0);


                //创建第一行
                HSSFRow row = sheet.createRow((short) 0);
                HSSFCell cell;
                row.setHeightInPoints(40);

                HSSFFont font = wb.createFont();
                font.setFontName("宋体");
                //粗体显示
                font.setBold(true);
                font.setFontHeightInPoints((short) 16);
                cell = row.createCell(0);
                cell.setCellValue("社区/村组");
                cell = row.createCell(1);
                cell.setCellValue("总金额(元)");
                cell = row.createCell(2);
                cell.setCellValue("去年总金额(元)");
                cell = row.createCell(3);
                cell.setCellValue("总人数(人)");
                cell = row.createCell(4);
                cell.setCellValue("去年总人数(人)");
                sheet.setColumnWidth(0, 4096);
                sheet.setColumnWidth(1, 4096);
                sheet.setColumnWidth(2, 4096);
                sheet.setColumnWidth(3, 4096);
                sheet.setColumnWidth(4, 4096);
                //设置列值-内容
                for (int j = 0; j < villageMap.size(); j++) {
                    row = sheet.createRow(j + 1);
                    row.setHeightInPoints(20);
                    cell = row.createCell(0);
                    cell.setCellValue((String) villageMap.get(j).get("village"));
                    cell = row.createCell(1);
                    cell.setCellValue(String.valueOf(Math.round((Double) villageMap.get(j).get("allmoney"))));
                    cell = row.createCell(2);
                    cell.setCellValue(String.valueOf(Math.round((Double) villageMap.get(j).get("lastyearallmoney"))));
                    cell = row.createCell(3);
                    cell.setCellValue(String.valueOf(villageMap.get(j).get("allpeople")));
                    cell = row.createCell(4);
                    cell.setCellValue(String.valueOf(villageMap.get(j).get("lastyearallpeople")));
                }
                row = sheet.createRow(villageMap.size() + 1);
                row.setHeightInPoints(20);
                cell = row.createCell(0);
                cell.setCellValue(allTownMap.get(n).get("town")+"总合计");
                cell = row.createCell(1);
                cell.setCellValue(String.valueOf(Math.round((Double) allTownMap.get(n).get("allmoney"))));
                cell = row.createCell(2);
                cell.setCellValue(String.valueOf(Math.round((Double) allTownMap.get(n).get("lastyearallmoney"))));
                cell = row.createCell(3);
                cell.setCellValue(String.valueOf(allTownMap.get(n).get("allpeople")));
                cell = row.createCell(4);
                cell.setCellValue(String.valueOf(allTownMap.get(n).get("lastyearallpeople")));
            }
        }
        //获得浏览器代理信息
        String finalFileName = null;
        try {
            finalFileName = DownLoadUtils.getFileName(request.getHeader("USER-AGENT"), "冠县社保统计数据清单.xls");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //设置HTTP响应头
        response.reset();        //重置 响应头
        response.setContentType("application/vnd.ms-excel");//告知浏览器下载文件，而不是直接打开，浏览器默认为打开
        response.addHeader("Content-Disposition", "attachment;filename=" + finalFileName + "");//下载文件的名称

        //将数据写入输出流
        OutputStream os = null;
        try {
            os = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 按镇或村为单位导出应缴人员信息
     */
    @Override
    public String exportFile(HttpServletResponse response, HttpServletRequest request, Map<String, Object> condition) {
        String result = "";
        List<Map<String, Object>> alltown = payableInfoMapper.getPayableAllTown(condition);
        long startTime = System.currentTimeMillis();
        HSSFWorkbook wb = new HSSFWorkbook();
        for (int i = 0; i < alltown.size(); i++) {
            //镇名
//                alltown.get(i).get("town");
            //获取要插入Excel的数据
            condition.put("town", alltown.get(i).get("town"));
            List<Map<String, Object>> gxswPayableInfoList = payableInfoMapper.getPayableInfo(condition);
            String sheetName = alltown.get(i).get("town") + "社保人员清单";
            HSSFSheet sheet = wb.createSheet(sheetName);

            /* 设置打印格式 */
            HSSFPrintSetup hps = sheet.getPrintSetup();
            hps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
            hps.setLandscape(true);
            hps.setFitHeight((short) 1);
            hps.setFitWidth((short) 1);
            hps.setScale((short) 65);
            hps.setFooterMargin(0);
            hps.setHeaderMargin(0);
            sheet.setMargin(HSSFSheet.TopMargin, 0.3);
            sheet.setMargin(HSSFSheet.BottomMargin, 0);
            sheet.setMargin(HSSFSheet.LeftMargin, 0.3);
            sheet.setMargin(HSSFSheet.RightMargin, 0);


            //创建第一行
            HSSFRow row = sheet.createRow((short) 0);
            HSSFCell cell;
            row.setHeightInPoints(40);

            HSSFFont font = wb.createFont();
            font.setFontName("宋体");
            //粗体显示
            font.setBold(true);
            font.setFontHeightInPoints((short) 16);
            cell = row.createCell(0);
            cell.setCellValue("身份证件号码");
            cell = row.createCell(1);
            cell.setCellValue("姓名");
            cell = row.createCell(2);
            cell.setCellValue("征收子目");
            cell = row.createCell(3);
            cell.setCellValue("街道乡镇");
            cell = row.createCell(4);
            cell.setCellValue("社区/村组");
            sheet.setColumnWidth(0, 4096);
            sheet.setColumnWidth(1, 4096);
            sheet.setColumnWidth(2, 4096);
            sheet.setColumnWidth(3, 4096);
            sheet.setColumnWidth(4, 4096);
            //设置列值-内容
            for (int j = 0; j < gxswPayableInfoList.size(); j++) {
                row = sheet.createRow(j + 1);
                row.setHeightInPoints(20);
                cell = row.createCell(0);
                cell.setCellValue((String) gxswPayableInfoList.get(j).get("credit_code"));
                cell = row.createCell(1);
                cell.setCellValue((String) gxswPayableInfoList.get(j).get("taxpayer_name"));
                cell = row.createCell(2);
                cell.setCellValue((String) gxswPayableInfoList.get(j).get("specific_item"));
                cell = row.createCell(3);
                cell.setCellValue((String) gxswPayableInfoList.get(j).get("town"));
                cell = row.createCell(4);
                cell.setCellValue((String) gxswPayableInfoList.get(j).get("village"));
            }

        }
        //获得浏览器代理信息
        String finalFileName = null;
        try {
            finalFileName = DownLoadUtils.getFileName(request.getHeader("USER-AGENT"), "冠县社保人员清单.xls");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //设置HTTP响应头
        response.reset();        //重置 响应头
        response.setContentType("application/vnd.ms-excel");//告知浏览器下载文件，而不是直接打开，浏览器默认为打开
        response.addHeader("Content-Disposition", "attachment;filename=" + finalFileName + "");//下载文件的名称

        //将数据写入输出流
        OutputStream os = null;
        try {
            os = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        //导出总耗时
        long time = endTime - startTime;
        return result;
    }
}
