package com.jxdinfo.hussar.gxsw.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jxdinfo.hussar.gxsw.dao.GxswJcsjInfoMapper;
import com.jxdinfo.hussar.gxsw.model.GxswJcsjInfo;
import com.jxdinfo.hussar.gxsw.service.IGxswJcsjInfoService;
import com.jxdinfo.hussar.util.ExcelSaxReader;
import com.jxdinfo.hussar.util.MyCallback;
import com.jxdinfo.hussar.util.NewExcelSaxReader;
import com.jxdinfo.hussar.util.bigdataexcel.BaseThreadPool;
import com.jxdinfo.hussar.util.bigdataexcel.SimpleThreadPool;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2019-10-17
 */
@Service
public class GxswJcsjInfoServiceImpl extends ServiceImpl<GxswJcsjInfoMapper, GxswJcsjInfo> implements IGxswJcsjInfoService {
    @Autowired
    ExcelSaxReader excelSaxReader;
    @Resource
    private GxswJcsjInfoMapper gxswJcsjInfoMapper;

/*
 @Autowired
    public Integer[] uploadOriginalData(MultipartFile file) throws IOException, InvalidFormatException, ExcelSaxReader.ParseException {
        //用于存放导入的结果信息
        Integer[] resultMap={0,0};
        InputStream inputStream=null;
        //新增数据条数
        int count=0;
        //新增多个map
        List<GxswJcsjInfo> gxswJcsjInfos=new ArrayList<>();
        long startTime=System.currentTimeMillis();
        //转换为输入流
        inputStream=file.getInputStream();
        ExcelSaxReader reader=excelSaxReader.parse(inputStream);
        List<String[]> datas=reader.getDatas();
        for(String[] str : datas) {
            GxswJcsjInfo gxswJcsjInfo=new GxswJcsjInfo();
            gxswJcsjInfo.setIdentityCard(str[0]);
            gxswJcsjInfo.setTaxpayerName(str[1]);
            gxswJcsjInfo.setTaxpayerItem(str[2]);
            gxswJcsjInfo.setTaxpayerMoney(str[3]);
            gxswJcsjInfo.setStartTime(str[4]);
            gxswJcsjInfo.setEndTime(str[5]);
            gxswJcsjInfo.setTaxpayerType(str[6]);
            gxswJcsjInfo.setTown(str[7]);
            gxswJcsjInfo.setVillage(str[8]);
            gxswJcsjInfos.add(gxswJcsjInfo);
        }
        long endTime=System.currentTimeMillis();
        long time =endTime-startTime;
        long startTime2=System.currentTimeMillis();
        resultMap[0]=gxswJcsjInfoMapper.batchInsert( gxswJcsjInfos);

        long endTime2=System.currentTimeMillis();
        long time2=endTime2-startTime2;
        long allTime=time+time2;
        resultMap[1]=(int)allTime;
        return resultMap;
    }
*/


    @Override
    public Map<String,Object> uploadOriginalData2(MultipartFile file) throws IOException, InvalidFormatException, ExcelSaxReader.ParseException, InterruptedException {


        long startTime = System.currentTimeMillis();
        ArrayBlockingQueue<String[]> queue = new ArrayBlockingQueue<>(100);

        //启动一个线程读取excel数据，将每行数据放入队列中。
        new Thread(()->{
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
        List<GxswJcsjInfo> list = new ArrayList<GxswJcsjInfo>(200000);
        boolean flag = true;
        int m = 0;
        int batchCount = 1;
        //分批执行插入的数量
        int eachBatchCount = 1000;

        while (flag){
            String[] rowData = queue.poll();
            if (rowData==null||rowData.length == 0 || rowData[0] == null){
                m++;
                if (m>100){
                    flag=false;
                } else{
                    Thread.currentThread().sleep(10);
                }
            }else {
                m = 0;
                GxswJcsjInfo gxswJcsjInfo = new GxswJcsjInfo();
                gxswJcsjInfo.setIdentityCard(rowData[0]);
                gxswJcsjInfo.setTaxpayerName(rowData[1]);
                gxswJcsjInfo.setTaxpayerItem(rowData[2]);
                gxswJcsjInfo.setTaxpayerMoney(rowData[3]);
                gxswJcsjInfo.setStartTime(rowData[4]);
                gxswJcsjInfo.setEndTime(rowData[5]);
                gxswJcsjInfo.setTaxpayerType(rowData[6]);
                gxswJcsjInfo.setTown(rowData[7]);
                gxswJcsjInfo.setVillage(rowData[8]);
                list.add(gxswJcsjInfo);

                //每100条批量插入
                if(list.size()>eachBatchCount*batchCount){
                    List<GxswJcsjInfo> eachBatchList  = new ArrayList<GxswJcsjInfo>(list.subList((batchCount-1)*eachBatchCount,batchCount*100));
                    batchCount++;
                    pool.execute(new BaseThreadPool.Execute() {
                        @Override
                        public void execute() {
                            gxswJcsjInfoMapper.batchInsert(eachBatchList);

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
                gxswJcsjInfoMapper.batchInsert(new ArrayList<GxswJcsjInfo>(list.subList((finalbatchCount -1)*eachBatchCount,list.size())));

            }
        });

        Map<String, Object> map = new HashMap();
        map.put("count", list.size());
        long endTime = System.currentTimeMillis();
        map.put("time", (endTime-startTime));
        return map;
    }



    /**
     * 获取条件下的基础数据
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> getList(Map<String, Object> map) {
        Map<String,Object> result = new HashMap<>(5);
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        int pageNumber = Integer.parseInt((String) map.get("pageNumber"));
        int startNumber = (pageNumber-1)*pageSize;
        //转成整数型
        map.put("startNumber",startNumber);
        map.put("pageSize",pageSize);

        int total = gxswJcsjInfoMapper.getCount(map);
        List<Map<String,Object>> payableInfoList = gxswJcsjInfoMapper.getList(map);
        result.put("total",total);
        result.put("rows",payableInfoList);
        return result;
    }
    /**
     * 查询所有的镇
     */
    @Override
    public List<Map<String,Object>> getAllTown (){
      return  gxswJcsjInfoMapper.getAllTown();
    }

    /**
     * 根据镇查询所有的村
     */
    @Override
    public List<Map<String,Object>> getAllVillage (Map<String,Object> map){
        return   gxswJcsjInfoMapper.getAllVillage(map);
    }
}
