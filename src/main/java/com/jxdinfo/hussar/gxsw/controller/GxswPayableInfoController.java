package com.jxdinfo.hussar.gxsw.controller;


import com.jxdinfo.hussar.gxsw.service.GxswPayableInfoService;
import com.jxdinfo.hussar.util.ExcelSaxReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author hasee
 */
@Controller
@RequestMapping("/payableInfo")
public class GxswPayableInfoController {
    @Autowired
    private GxswPayableInfoService payableInfoService;
    @Autowired
    private ExcelSaxReader excelSaxReader;
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(GxswPayableInfoController.class);

    /**
     * 跳转冠县总统计页面
     */
    @RequestMapping("/goView")
    public String goView(Model model) {
        return "/gxsw/townpayable.html";
    }

    /**
     * 冠县总统计合计数据
     */
    @RequestMapping(value = "/aggregatedData")
    @ResponseBody
    public Object aggregatedData(@RequestParam Map<String,Object> map) {
        Map<String,Object> guanxianInfo = payableInfoService.guanxianInfo(map);
        return guanxianInfo;
    }

    /**
     * 按镇统计数据
     */
    @RequestMapping(value = "/getTownPayableList")
    @ResponseBody
    public Object getTownPayableList(@RequestParam Map<String,Object> map, Model model) {
        map.put("way", "table");
        Map<String, Object> list = new HashMap<>(5);
        list = payableInfoService.getTownPayableList(map);
        return list;
    }

    /**
     * 跳转到该镇的村的统计页面
     */
    @RequestMapping(value = "/getVillagePayableList")
    public String gotoVillagePage(@RequestParam("allMoney") String allMoney,
                                  @RequestParam("allPeople") String allPeople,
                                  @RequestParam("lastyearallMoney") String lastyearallMoney,
                                  @RequestParam("lastyearallPeople") String lastyearallPeople,
                                  @RequestParam("town") String town,
                                  @RequestParam("year") String year,Model model) {
        model.addAttribute("town",town);
        model.addAttribute("allMoney",allMoney);
        model.addAttribute("allPeople",allPeople);
        model.addAttribute("year",year);
        model.addAttribute("lastyearallMoney",lastyearallMoney);
        model.addAttribute("lastyearallPeople",lastyearallPeople);

        return "/gxsw/villagepayable.html";
    }

    /**
     * 按村统计数据
     */
    @RequestMapping(value = "/VillagePayableList")
    @ResponseBody
    public Object getVillagePayableList(@RequestParam Map<String,Object> map,@RequestParam String town) {
        Map<String, Object> list = new HashMap<>(5);
        map.put("way", "table");
        map.put("town",town);
        list = payableInfoService.getVillagePayableList(map);
        return list;
    }

    /**
     * 跳转到该村人员详情的统计页面
     */
    @RequestMapping(value = "/getPayableInfo")
    public String gotoInfoPage(@RequestParam("allMoney") String allMoney,
                               @RequestParam("allPeople") String allPeople,
                               @RequestParam("town") String town,
                               @RequestParam("village") String village,
                               @RequestParam("year") String year,Model model) {
        model.addAttribute("town",town);
        model.addAttribute("village",village);
        model.addAttribute("allMoney",allMoney);
        model.addAttribute("allPeople",allPeople);
        model.addAttribute("year",year);
        return "/gxsw/payableinfo1.html";
    }


    /**
     * 获取该村的详情
     */
    @RequestMapping(value = "/getPayableInfoList")
    @ResponseBody
    public Object getPayableInfoList(@RequestParam Map<String,Object> map,
                                     @RequestParam String town,
                                     @RequestParam String village) {
        map.put("way", "table");
        map.put("town",town);
        map.put("village",village);
        Map<String, Object> list = new HashMap<>(5);
        list = payableInfoService.getPayableInfoList(map);
        return list;
    }

    /**
     * 导出该村的详情
     */
    @RequestMapping("/exportVillageFile")
    public Map<String, Object> exportVillageFile(@RequestParam("year") String year,
                                                 @RequestParam("town") String town,
                                                 @RequestParam("village") String village,
                                                 HttpServletResponse response, HttpServletRequest request) {
        String result = "";
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("year",year);
        condition.put("town",town);
        condition.put("village",village);
        result = payableInfoService.exportVillageFile(response, request,condition);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", result);
        return map;
    }

    /**
     * 上传实际缴纳社保的人员数据
     * @param file
     * @return
     */
    @RequestMapping("/uploadDetailData")
    @ResponseBody
    public Map<String, Object> uploadAccountingData(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("year")String year) throws
            ExcelSaxReader.ParseException, InvalidFormatException, IOException, InterruptedException {
        Map<String, Object> map = new HashMap<>();
        String fileName =  file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (suffix.equals("xlsx")) {
            map = payableInfoService.uploadDetailData(file,year);
        } else {
            map.put("judge","1");
        }
        return map;
    }

    /**
     * 到处统计数据
     */
    @RequestMapping("/exportstatistics")
    public Map<String, Object> exportStatistics(@RequestParam("year") String year,HttpServletResponse response, HttpServletRequest request) {
        String result = "";
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("year",year);
        result = payableInfoService.exportStatistics(response, request,condition);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", result);
        return map;
    }

    /**
     * 以乡镇或村为单位导出应缴人员信息
     */
    @RequestMapping("/exportFile")
    public Map<String, Object> exportTownFile(@RequestParam("year") String year,HttpServletResponse response, HttpServletRequest request) {
        String result = "";
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("year",year);
        result = payableInfoService.exportFile(response, request,condition);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", result);
        return map;
    }

}
