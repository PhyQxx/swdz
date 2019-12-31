package com.jxdinfo.hussar.gxsw.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.hussar.core.log.LogObjectHolder;
import com.jxdinfo.hussar.gxsw.model.GxswJcsjInfo;
import com.jxdinfo.hussar.gxsw.service.IGxswJcsjInfoService;
import com.jxdinfo.hussar.util.ExcelSaxReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author 
 * @Date 2019-10-17 10:14:11
 */
@Controller
@RequestMapping("/gxswJcsjInfo")
public class GxswJcsjInfoController extends BaseController {

    private String PREFIX = "/gxsw/gxswJcsjInfo/";

    @Autowired
    private IGxswJcsjInfoService gxswJcsjInfoService;

    /**
     * 跳转到首页
     */
    @RequestMapping("/view")
    public String index(Model model) {

        List<Map<String, Object>> towns = gxswJcsjInfoService.getAllTown();
        model.addAttribute("towns",towns);
        return PREFIX + "gxswJcsjInfo.html";
    }







    /**
     * 跳转到添加
     */
    @RequestMapping("/gxswJcsjInfo_add")
    public String gxswJcsjInfoAdd() {
        return PREFIX + "gxswJcsjInfo_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/gxswJcsjInfo_update/{gxswJcsjInfoId}")
    public String gxswJcsjInfoUpdate(@PathVariable String gxswJcsjInfoId, Model model) {
        GxswJcsjInfo gxswJcsjInfo = gxswJcsjInfoService.selectById(gxswJcsjInfoId);
        model.addAttribute("item", gxswJcsjInfo);
        LogObjectHolder.me().set(gxswJcsjInfo);
        return PREFIX + "gxswJcsjInfo_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition,
                       @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                       @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
        Page<GxswJcsjInfo> page = new Page<>(pageNumber, pageSize);
        Wrapper<GxswJcsjInfo> ew = new EntityWrapper<>();
        Map<String, Object> result = new HashMap<>(5);
        List<GxswJcsjInfo> list = gxswJcsjInfoService.selectPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(GxswJcsjInfo gxswJcsjInfo) {
        gxswJcsjInfoService.insert(gxswJcsjInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String gxswJcsjInfoId) {
        gxswJcsjInfoService.deleteById(gxswJcsjInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(GxswJcsjInfo gxswJcsjInfo) {
        gxswJcsjInfoService.updateById(gxswJcsjInfo);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{gxswJcsjInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("gxswJcsjInfoId") String gxswJcsjInfoId) {
        return gxswJcsjInfoService.selectById(gxswJcsjInfoId);
    }


    /**
     * 上传原始数据
     */
    @RequestMapping("/uploadOriginalData")
    @ResponseBody
    public Map<String, Object> uploadAccountingData(@RequestParam("file") MultipartFile file) throws ExcelSaxReader.ParseException, InvalidFormatException, IOException, InterruptedException {
        return gxswJcsjInfoService.uploadOriginalData2(file);
    }

    /**
     * 用于获取一定条件的基础数据
     * @param map
     * @return
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Object getTownPayableList(@RequestParam Map<String,Object> map) {
        Map<String, Object> list = new HashMap<>(5);
        list = gxswJcsjInfoService.getList(map);
        return list;
    }


    /**
     * 根据镇查询所有的村
     * @param map
     * @return
     */
    @RequestMapping(value = "/getAllVillage")
    @ResponseBody
    public Object getAllVillage(@RequestParam Map<String,Object> map) {
       List <Map<String, Object> >list = new ArrayList<>();
        list = gxswJcsjInfoService.getAllVillage(map);
        return list;
    }
}
