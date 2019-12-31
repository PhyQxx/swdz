package com.jxdinfo.hussar.gxsw.service.impl;


import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.jxdinfo.hussar.gxsw.dao.GxswSocialSecurityDataMapper;
import com.jxdinfo.hussar.gxsw.dao.GxswTaxBureauDataMapper;
import com.jxdinfo.hussar.gxsw.model.GxswSocialSecurityData;
import com.jxdinfo.hussar.gxsw.model.GxswTaxBureauData;
import com.jxdinfo.hussar.gxsw.service.ReconciliationService;
import com.jxdinfo.hussar.util.*;
import com.jxdinfo.hussar.util.bigdataexcel.BaseThreadPool;
import com.jxdinfo.hussar.util.bigdataexcel.Callback;
import com.jxdinfo.hussar.util.bigdataexcel.ExcelUtil;
import com.jxdinfo.hussar.util.bigdataexcel.SimpleThreadPool;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author hasee
 */
@Service
public class ReconciliationServiceImpl implements ReconciliationService {
    @Autowired
    ExcelSaxReader excelSaxReader;
    @Autowired
    GxswSocialSecurityDataMapper gxswSocialSecurityDataMapper;
    @Autowired
    GxswTaxBureauDataMapper gxswTaxBureauDataMapper;
    @Value("${hussar.download-path}")
    String realPath;

    @Override
    public List<Map<String,Object>> getSpecificItem() {

        return gxswTaxBureauDataMapper.getSpecificItem();
    }
    @Override
    public Map<String, Object> getCompareResultList(Map<String, Object> map) {
        Map<String,Object> result = new HashMap<>(7);
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        int pageNumber = Integer.parseInt((String) map.get("pageNumber"));
        int startNumber = (pageNumber-1)*pageSize;
        map.put("startNumber",startNumber);
        map.put("pageSize",pageSize);
        int total = gxswTaxBureauDataMapper.getCompareResultCount(map);
        List<Map<String, Object>> compareResultList = gxswTaxBureauDataMapper.getCompareResultList(map);
        result.put("total",total);
        result.put("rows",compareResultList);
        return result;
    }

    /**
     * 补缴人员信息列表
     * @param backckPaymentMap
     * @return
     */
    @Override
    public Map<String, Object> getBackPaymentList(Map<String, Object> backckPaymentMap) {
        Map<String,Object> result = new HashMap<>(7);
        int pageSize = Integer.parseInt((String) backckPaymentMap.get("pageSize"));
        int pageNumber = Integer.parseInt((String) backckPaymentMap.get("pageNumber"));
        int startNumber = (pageNumber-1)*pageSize;
        backckPaymentMap.put("startNumber",startNumber);
        backckPaymentMap.put("pageSize",pageSize);
        int total = gxswTaxBureauDataMapper.getBackPaymentCount(backckPaymentMap);
        List<Map<String, Object>> backPaymentList = gxswTaxBureauDataMapper.getBackPaymentList(backckPaymentMap);
        result.put("total",total);
        result.put("rows",backPaymentList);
        return result;
    }


    /**
     * 上传社保数据
     */

    @Override
    public Map<String, Object> uploadPaymentData(MultipartFile file, String year) {
        long startTime = System.currentTimeMillis();
        //清空表中数据
        gxswTaxBureauDataMapper.deleteSocialData(year);

        ArrayBlockingQueue<String[]> queue = new ArrayBlockingQueue<>(100);
        //启动一个线程读取excel数据，将每行数据放入阻塞队列中。
        new Thread(()-> {
            NewExcelSaxReader excelSaxReader = new NewExcelSaxReader(new MyCallback(){
                @Override
                public void callback(String[] strings) throws InterruptedException {
                    queue.put(strings);
                }
            });
            try {
                excelSaxReader.parse(file.getInputStream());
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NewExcelSaxReader.ParseException e) {
                e.printStackTrace();
            }

        }).start();

        //获取队列中的数据（读取Excel的数据），多线程批量插入数据库中
        SimpleThreadPool pool = new SimpleThreadPool(5);
        List<GxswSocialSecurityData> list = new ArrayList<>(200000);
        boolean flag =true;
        int m=0;
        int batchCount = 1;
        //分批执行插入的数量
        int eachBatchCount = 10000;
        while (flag){
            String[] rowData = queue.poll();
            if (rowData==null||rowData.length == 0 || rowData[0] == null){
                m++;
                if (m>100){
                    flag=false;
                } else{
                    try {
                        Thread.currentThread().sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }else {
                m = 0;
                GxswSocialSecurityData gxswSocialSecurityData = new GxswSocialSecurityData();
                gxswSocialSecurityData.setId(IdWorker.get32UUID());
                gxswSocialSecurityData.setSocialSecurityNumber(rowData[0]);
                gxswSocialSecurityData.setName(rowData[1]);
                gxswSocialSecurityData.setInsuredGroup(rowData[2]);
                gxswSocialSecurityData.setPersonnelCategory(rowData[3]);
                gxswSocialSecurityData.setDeclarationCategory(rowData[4]);
                gxswSocialSecurityData.setStartDate(rowData[5]);
                gxswSocialSecurityData.setEndDate(rowData[6]);
                gxswSocialSecurityData.setPaymentDate(rowData[7]);
                gxswSocialSecurityData.setPersonalPayment(rowData[8]);
                gxswSocialSecurityData.setTown(rowData[9]);
                gxswSocialSecurityData.setVillage(rowData[10]);
                gxswSocialSecurityData.setYear(year);
                list.add(gxswSocialSecurityData);

                if (list.size()>eachBatchCount*batchCount){
                    List<GxswSocialSecurityData> eachBatchList = new ArrayList<>(list.subList((batchCount-1)*eachBatchCount,batchCount*eachBatchCount));
                    batchCount++;
                    pool.execute(new BaseThreadPool.Execute() {
                        @Override
                        public void execute() {
                            gxswSocialSecurityDataMapper.batchInsertAll(eachBatchList);
                        }
                    });
                }
            }
        }

        //插入list最后不到100条数据
        int finalbatchCount = batchCount;
        if(list != null && list.size()>0){
            pool.execute(new BaseThreadPool.Execute() {
                @Override
                public void execute() {
                    gxswSocialSecurityDataMapper.batchInsertAll
					(new ArrayList<GxswSocialSecurityData>(list.subList
					((finalbatchCount -1)*eachBatchCount,list.size())));
                }
            });
        }

        Map<String, Object> map = new HashMap();
        map.put("count", list.size());
        long endTime = System.currentTimeMillis();
        map.put("time", (endTime-startTime)/1000);
        return map;
    }

    /**
     * 上传对账数据
     * @param file 上传文件
     * @return 上传结果
     */
    @Override
    public Map<String,Object> uploadAccountingData(MultipartFile file,String year) {
        long startTime = System.currentTimeMillis();
        //清空表中数据
        gxswTaxBureauDataMapper.deleteTaxData(year);
        //用于存放导入的结果信息

        ArrayBlockingQueue<String[]> queue = new ArrayBlockingQueue<>(100);
        //启动一个线程读取excel数据，将每行数据放入阻塞队列中。
        new Thread(()-> {
            NewExcelSaxReader excelSaxReader = new NewExcelSaxReader(new MyCallback(){
                @Override
                public void callback(String[] strings) throws InterruptedException {
                    queue.put(strings);
                }
            });
            try {
                excelSaxReader.parse(file.getInputStream());
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NewExcelSaxReader.ParseException e) {
                e.printStackTrace();
            }
        }).start();
        SimpleThreadPool pool = new SimpleThreadPool(5);
        List<GxswTaxBureauData> list = new ArrayList<>(100000);
        boolean flag =true;
        int m=0;
        int batchCount = 1;
        //分批执行插入的数量
        int eachBatchCount = 10000;
        while (flag){
            String[] rowData = queue.poll();
            if (rowData==null||rowData.length == 0 || rowData[0] == null){
                m++;
                if (m>100){
                    flag=false;
                } else{
                    try {
                        Thread.currentThread().sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }else {
                m = 0;
                GxswTaxBureauData gxswTaxBureauData = new GxswTaxBureauData();
                gxswTaxBureauData.setId(IdWorker.get32UUID());
                gxswTaxBureauData.setSocialCreditCode(rowData[0]);
                gxswTaxBureauData.setTaxpayerName(rowData[1]);
                gxswTaxBureauData.setTaxItem(rowData[2]);
                gxswTaxBureauData.setTaxPayment(rowData[3]);
                gxswTaxBureauData.setStartDate(rowData[4]);
                gxswTaxBureauData.setEndDate(rowData[5]);
                String taxType = rowData[6];
                if(taxType==null){
                    gxswTaxBureauData.setTaxType(taxType);
                }else{
                    taxType=taxType.substring((taxType.indexOf("-")+1),taxType.length());
                }
                gxswTaxBureauData.setTaxType(taxType);
                gxswTaxBureauData.setTown(rowData[7]);
                gxswTaxBureauData.setVillage(rowData[8]);
                gxswTaxBureauData.setYear(year);
                list.add(gxswTaxBureauData);
                if (list.size()>eachBatchCount*batchCount){
                    List<GxswTaxBureauData> eachBatchList = new ArrayList<>(list.subList((batchCount-1)*eachBatchCount,batchCount*eachBatchCount));
                    batchCount++;
                    pool.execute(new BaseThreadPool.Execute() {
                        @Override
                        public void execute() {
                            gxswTaxBureauDataMapper.batchInsertAll(eachBatchList);
                        }
                    });
                }
            }
        }
        //插入list中最后不到100整数的数据
        int finalbatchCount = batchCount;
        pool.execute(new BaseThreadPool.Execute() {
            @Override
            public void execute() {
                gxswTaxBureauDataMapper.batchInsertAll(new ArrayList<GxswTaxBureauData>(list.subList((finalbatchCount -1)*eachBatchCount,list.size())));

            }
        });

        Map<String, Object> map = new HashMap();
        map.put("count", list.size());
        long endTime = System.currentTimeMillis();
        map.put("time", (endTime-startTime)/1000);
        return map;
    }
    /**
     * 导出异常数据
     * @param response
     * @param request
     * @return
     */
    @Override
    public void exportExceptionData(HttpServletResponse response, HttpServletRequest request,Map<String,Object> resultMap) {
        String sheetName = null;
        String fileName = null;
        if (resultMap.get("specificItem")==""){
            sheetName = resultMap.get("year")+"冠县人员异常数据";
            fileName = resultMap.get("year")+"冠县人员异常数据.xls";
        }else {
            sheetName = resultMap.get("year")+""+resultMap.get("specificItem")+"冠县人员异常数据";
            fileName = resultMap.get("year")+""+resultMap.get("specificItem")+"冠县人员异常数据.xls";
        }

        response.setContentType("octets/stream");
        try {
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);//创建一个sheet-test1

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

        //设置单元格风格，居中对齐.
        HSSFCellStyle cs = wb.createCellStyle();
        HSSFCellStyle headStyle = PlDao.getCellGreenStyle(wb);// 表头样式
        HSSFCellStyle cellStyle = PlDao.createEditStyle(wb);// 单元格样式——数据
        HSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font1.setFontHeightInPoints((short) 10);
        cellStyle.setFont(font1);
        cellStyle.setWrapText(true);


        //创建第一行
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell ;
        row.setHeightInPoints(40);
        cell = row.createCell(0);
        cell.setCellValue("人社局数据");
        cell = row.createCell(5);
        cell.setCellValue("税务局数据");
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setBold(true);//粗体显示
        font.setFontHeightInPoints((short) 16);
        cs.setAlignment(HorizontalAlignment.CENTER);
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        cs.setFont(font);
        cs.setBorderBottom(BorderStyle.THIN);
        cs.setBorderLeft(BorderStyle.THIN);
        cs.setBorderTop(BorderStyle.THIN);
        cs.setBorderRight(BorderStyle.THIN);
        cell.setCellStyle(cs);
        CellRangeAddress region1 = new CellRangeAddress(0,  0, 0, (short) 4);
        sheet.addMergedRegion(region1);
        CellRangeAddress region2 = new CellRangeAddress(0,  0, 5, (short) 9);
        sheet.addMergedRegion(region2);
        //创建第二行
        row = sheet.createRow(1);
        row.setHeightInPoints(30);
        font.setFontName("宋体");
        //粗体显示
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);//设置字体大小
        cell = row.createCell(0);
        cell.setCellValue("身份证号码");
        cell = row.createCell(1);
        cell.setCellValue("姓名");
        cell = row.createCell(2);
        cell.setCellValue("缴费金额");
        cell = row.createCell(3);
        cell.setCellValue("征收子目");
        cell = row.createCell(4);
        cell.setCellValue("年份");
        cell = row.createCell(5);
        cell.setCellValue("身份证号");
        cell = row.createCell(6);
        cell.setCellValue("姓名");
        cell = row.createCell(7);
        cell.setCellValue("缴费金额");
        cell = row.createCell(8);
        cell.setCellValue("征收子目");
        cell = row.createCell(9);
        cell.setCellValue("年份");
        sheet.setColumnWidth(0, 4096);
        sheet.setColumnWidth(1, 4096);
        sheet.setColumnWidth(2, 4096);
        sheet.setColumnWidth(3, 4096);
        sheet.setColumnWidth(4, 4096);
        sheet.setColumnWidth(5, 4096);
        sheet.setColumnWidth(6, 4096);
        sheet.setColumnWidth(7, 4096);
        sheet.setColumnWidth(8, 4096);
        sheet.setColumnWidth(9, 4096);
        //获取所有的征收子目
        List<Map<String, Object>> specificItemList = gxswTaxBureauDataMapper.getSpecificItem();
        //遍历所有征收子目
        List<Map<String, Object>> compareResultList;
        for(Map<String,Object> specificItemMap:specificItemList) {
            if (resultMap.get("specificItem")=="") {
                specificItemMap.put("year", resultMap.get("year"));
                compareResultList = gxswTaxBureauDataMapper.getCompareResultList(specificItemMap);
            } else {
                compareResultList = gxswTaxBureauDataMapper.getCompareResultList(resultMap);
            }
            for (int i = 0; i < compareResultList.size(); i++) {
                row = sheet.createRow(i + 2);
                row.setHeightInPoints(20);
                Iterator<Map.Entry<String, Object>> iterator = compareResultList.get(i).entrySet().iterator();
                int count = 0;
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    cell = row.createCell(count);
                    cell.setCellValue(entry.getValue().toString());
                    count++;
                }
            }
            OutputStream out = null;
            try {
                out = response.getOutputStream();
                wb.write(out);
            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 导出补缴人员数据
     * @return
     */
    @Override
    public Map<String, Object> exportPaymentData(HttpServletResponse response, HttpServletRequest request,Map<String,Object> resultMap) {
        String sheetName = resultMap.get("year") + "冠县补缴人员信息";
        String fileName = resultMap.get("year") + "冠县补缴人员信息.xls";
        response.setContentType("octets/stream");
        try {
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);//创建一个sheet页
        /**
         * 设置打印格式
         */
        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
        printSetup.setLandscape(true);
        printSetup.setFitHeight((short) 1);
        printSetup.setFitWidth((short) 1);
        printSetup.setScale((short) 65);
        printSetup.setFooterMargin(0);
        printSetup.setHeaderMargin(0);
        sheet.setMargin(HSSFSheet.TopMargin, 0.3);
        sheet.setMargin(HSSFSheet.BottomMargin, 0);
        sheet.setMargin(HSSFSheet.LeftMargin, 0.3);
        sheet.setMargin(HSSFSheet.RightMargin, 0);

        //设置单元格风格，居中对齐.
        HSSFCellStyle cs = workbook.createCellStyle();
        HSSFCellStyle headStyle = PlDao.getCellGreenStyle(workbook);// 表头样式
        HSSFCellStyle cellStyle = PlDao.createEditStyle(workbook);// 单元格样式——数据
        HSSFFont font1 = workbook.createFont();
        font1.setFontName("宋体");
        font1.setFontHeightInPoints((short) 10);
        cellStyle.setFont(font1);
        cellStyle.setWrapText(true);

        //创建第一行
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell ;
        row.setHeightInPoints(40);
        cell = row.createCell(0);
        cell.setCellValue(resultMap.get("year") + "人社局补缴人员数据");
        cell = row.createCell(6);
        cell.setCellValue(resultMap.get("year") + "税务局补缴人员数据");
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setBold(true);//粗体显示
        font.setFontHeightInPoints((short) 16);
        cs.setAlignment(HorizontalAlignment.CENTER);
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        cs.setFont(font);
        cs.setBorderBottom(BorderStyle.THIN);
        cs.setBorderLeft(BorderStyle.THIN);
        cs.setBorderTop(BorderStyle.THIN);
        cs.setBorderRight(BorderStyle.THIN);
        cell.setCellStyle(cs);
        CellRangeAddress region = new CellRangeAddress(0,  0, 0,  5);
        sheet.addMergedRegion(region);
        CellRangeAddress region1 = new CellRangeAddress(0, 0, 6,  11);
        sheet.addMergedRegion(region1);
        //创建第二行
        row = sheet.createRow(1);
        row.setHeightInPoints(30);
        font.setFontName("宋体");
        //粗体显示
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);//设置字体大小
        cell = row.createCell(0);
        cell.setCellValue("身份证号码");
        cell = row.createCell(1);
        cell.setCellValue("姓名");
        cell = row.createCell(2);
        cell.setCellValue("起始年份");
        cell = row.createCell(3);
        cell.setCellValue("终止年份");
        cell = row.createCell(4);
        cell.setCellValue("缴费金额");
        cell = row.createCell(5);
        cell.setCellValue("缴费年份");
        cell = row.createCell(6);
        cell.setCellValue("身份证号码");
        cell = row.createCell(7);
        cell.setCellValue("姓名");
        cell = row.createCell(8);
        cell.setCellValue("起始年份");
        cell = row.createCell(9);
        cell.setCellValue("终止年份");
        cell = row.createCell(10);
        cell.setCellValue("缴费金额");
        cell = row.createCell(11);
        cell.setCellValue("缴费年份");
        sheet.setColumnWidth(0, 4096);
        sheet.setColumnWidth(1, 4096);
        sheet.setColumnWidth(2, 4096);
        sheet.setColumnWidth(3, 4096);
        sheet.setColumnWidth(4, 4096);
        sheet.setColumnWidth(5, 4096);
        sheet.setColumnWidth(6, 4096);
        sheet.setColumnWidth(7, 4096);
        sheet.setColumnWidth(8, 4096);
        sheet.setColumnWidth(9, 4096);
        sheet.setColumnWidth(10, 4096);
        sheet.setColumnWidth(11, 4096);
        List<Map<String, Object>> backPaymentList = gxswTaxBureauDataMapper.getBackPaymentList(resultMap);
        for (int i = 0; i < backPaymentList.size(); i++) {
            row = sheet.createRow(i + 2);
            row.setHeightInPoints(20);
            Iterator<Map.Entry<String, Object>> iterator = backPaymentList.get(i).entrySet().iterator();
            int count = 0;
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                cell = row.createCell(count);
                cell.setCellValue(entry.getValue().toString());
                count++;
            }
        }
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

    }

    @Override
    public Map<String,Object> saveErrorData(Map<String, Object> map) {
        Map<String,Object> result = new HashMap<>(5);
        gxswTaxBureauDataMapper.deleteErrorData(map);
        int total = gxswTaxBureauDataMapper.saveErrorData(map);
        result.put("total",total);
        return result;
    }
}
