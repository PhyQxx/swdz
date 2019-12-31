package com.jxdinfo.hussar.gxsw.controller;


import com.jxdinfo.hussar.gxsw.service.ReconciliationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 用于处理对账相关
 * @author hasee
 */
@Controller
@RequestMapping("/reconciliation")
public class ReconciliationController {
    @Autowired
    private ReconciliationService reconciliationService;
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(ReconciliationController.class);

    /**
     * 跳转专家评分页面
     */
    @RequestMapping("/goView")
    public String goView(Model model ) {
        Map<String,Object> map = new HashMap<String, Object>(5);
        map.put("item","普通农民");
        List<Map<String,Object>> list = new ArrayList<>(5);
        list.add(map);
        List<Map<String,Object>> specificItems = reconciliationService.getSpecificItem();
        if(specificItems!=null){
            model.addAttribute("specificItems",specificItems);
        }else {
            model.addAttribute("specificItems",list);
        }
        model.addAttribute("specificItems",specificItems);
        return "/gxsw/reconciliation.html";
    }

    /**
     * 跳到补交人员信息页面
     * @return
     */
    @RequestMapping("/goBackPayment")
    public String goBackPayment(){
        return "/gxsw/backPayment.html";
    }


    /**
     * 获取对比结果列表
     * @param map 查询参数
     * @return 获取对比结果列表
     */
    @RequestMapping(value = "/getCompareResultList")
    @ResponseBody
    public Object getCompareResultList(@RequestParam Map<String,Object> map) {
        map.put("way", "table");
        Map<String, Object> list = new HashMap<>(7);
        try{
            list = reconciliationService.getCompareResultList(map);
        }catch(Exception e){
            e.printStackTrace();
            ReconciliationController.logger.error("获取对比结果列表，getCompareResultList方法报错：" + e.getMessage());
        }
        return list;
    }

    /**
     * 获取补缴人员列表
     * @param map
     * @return
     */
    @RequestMapping("/getBackPayment")
    @ResponseBody
    public Object getBackPaymentList(@RequestParam Map<String,Object> map){
        map.put("way","table");
        Map<String,Object> list = new HashMap<>(7);
        try {
            list= reconciliationService.getBackPaymentList(map);
        }catch (Exception e){
            e.printStackTrace();
            ReconciliationController.logger.error("获取对比结果列表，getBackPayment方法报错：" + e.getMessage());
        }
        return  list;
    }

    /**
     * 导出异常数据
     * @param response
     * @param request
     * @return
     */

    @RequestMapping("/exportExceptionData")
    public Object exportExceptionData(HttpServletResponse response, HttpServletRequest request, @RequestParam Map<String,Object> map){
        System.out.println(map);
        Map<String,Object> resultMap = new HashMap<>();
        try {
            reconciliationService.exportExceptionData(response, request,map);
        }catch (Exception e){
            e.printStackTrace();
            ReconciliationController.logger.error("导出异常数据出错，exportExceptionData方法报错"+e.getMessage());
        }
        return resultMap;
    }

    /**
     * 导出补缴人员数据
     * @return
     */
    @RequestMapping("/exportPaymentData")
    @ResponseBody
    public Object exportPaymentData(HttpServletResponse response, HttpServletRequest request,@RequestParam Map<String,Object> resultMap){
        Map<String, Object> map = new HashMap<>();
        try {
            map = reconciliationService.exportPaymentData(response,request,resultMap);
        }catch (Exception e){
            e.printStackTrace();
            ReconciliationController.logger.error("导出异常数据出错，exportPaymentData方法报错"+e.getMessage());
        }
        return map;
    }

    /**
     * 上传缴费数据
     */
    @RequestMapping(value = "/uploadPaymentData", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadPaymentData(@RequestParam("file") MultipartFile file,@RequestParam("data") String year){
        System.out.println(year);
         Map<String,Object> map = reconciliationService.uploadPaymentData(file,year);
        return map;
    }

    /**
     * 上传记账数据
     */
    @RequestMapping(value = "/uploadAccountingData", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadAccountingData(@RequestParam("file") MultipartFile file,@RequestParam("data") String year){
        System.out.println(year);
        Map<String, Object> map = new HashMap<String, Object>(5);
        map = reconciliationService.uploadAccountingData(file,year);
        return map;
    }

    /**
     * 获取所有的征收子目
     * @param map
     * @return
     */
    @RequestMapping("/getAllSpecificItem")
    @ResponseBody
    public Object getAllSpecificItem(@RequestParam Map<String,Object> map) {
        List<Map<String,Object>> list = new ArrayList<>();
        try {
            list = reconciliationService.getSpecificItem();
        }catch (Exception e){
            e.printStackTrace();
            ReconciliationController.logger.error("查询征收子目，getAllSpecificItem报错：" + e.getMessage());
        }
        return list;
    }

    /**
     * 导出补缴人员数据
     * @return
     */
    @RequestMapping("/saveErrorData")
    @ResponseBody
    public Object saveErrorData(@RequestParam Map<String,Object> map){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = reconciliationService.saveErrorData(map);
        }catch (Exception e){
            e.printStackTrace();
            ReconciliationController.logger.error("对账异常数据保存出错，saveErrorData方法报错"+e.getMessage());
        }
        return resultMap;
    }
}
