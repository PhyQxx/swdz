package com.jxdinfo.hussar.util.bigdataexcel;

import dm.jdbc.util.StringUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author wenqingkuan
 * @date 2019-10-21 11:21
 */
public class ExcelXlsxHandle extends DefaultHandler {

    private CellDataType nextDataType=CellDataType.SSTINDEX;;
    private int formatIndex;
    private String formatString;
    private SharedStringsTable sst;

    /**
     * 单元格中的数据可能的数据类型
     */
    enum CellDataType {
        BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER, DATE, NULL
    }


    private boolean isAvailabledOfRow = false;// 是否是有效行
    private int availabledRows = 0;
    private int totalRows = 0;
    private int currentRowNum = 0;
    private Map<String, String> cellMap = null;
    private String key;
    private String lastIndex;
    private Callback callback;


    public ExcelXlsxHandle(Callback callback, XSSFReader reader) {
        this.callback = callback;
        try {
            sst=reader.getSharedStringsTable();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("row".equalsIgnoreCase(qName)) {// 如果是行元素
            totalRows++;// 总行数+1
            String r = attributes.getValue("r");// 获取行号
            currentRowNum = Integer.parseInt(r);
            cellMap= new HashMap<>();
            isAvailabledOfRow=false;
        } else if ("c".equalsIgnoreCase(qName)) {// 如果是单元格
            key = getKey(attributes);// 获取键值
            cellMap.put(key, null);// 先放入map,单此时值为null
            this.setNextDataType(attributes);
        }
    }

    private String getKey(Attributes attributes){
        return attributes.getValue("r").replaceAll("\\d*","");
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //      super.characters(ch, start, length);
        lastIndex = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //super.endElement(uri, localName, qName);
        if ("v".equalsIgnoreCase(qName)) {// 如果是值标签
            String value = this.getDataValue(lastIndex.trim());
            if (!StringUtil.isEmpty(value)){
                isAvailabledOfRow=true;
            }
            cellMap.put(key, value);// 重设值
        } else if ("c".equalsIgnoreCase(qName)) {
            key = null;// key置位null
            lastIndex = null;// lastIndex置位null
        } else if ("row".equalsIgnoreCase(qName)) {// 如果row是结束标签,说明一行结束
            if (currentRowNum>1 && isAvailabledOfRow) {// 如果是有效行
                availabledRows++;// 是有效行则有效行数+1
                callback.callback(cellMap,currentRowNum,availabledRows);// 回调,将结果输送给客户端,让客户端处理
            }
        }
    }

    /**
     * 处理数据类型
     *
     * @param attributes
     */
    public void setNextDataType(Attributes attributes) {
        nextDataType = CellDataType.NUMBER; //cellType为空，则表示该单元格类型为数字
        formatIndex = -1;
        formatString = null;
        String cellType = attributes.getValue("t"); //单元格类型
        String cellStyleStr = attributes.getValue("s"); //
        String columnData = attributes.getValue("r"); //获取单元格的位置，如A1,B1

        if ("b".equals(cellType)) { //处理布尔值
            nextDataType = CellDataType.BOOL;
        } else if ("e".equals(cellType)) {  //处理错误
            nextDataType = CellDataType.ERROR;
        } else if ("inlineStr".equals(cellType)) {
            nextDataType = CellDataType.INLINESTR;
        } else if ("s".equals(cellType)) { //处理字符串
            nextDataType = CellDataType.SSTINDEX;
        } else if ("str".equals(cellType)) {
            nextDataType = CellDataType.FORMULA;
        }
    }

    /**
     * 对解析出来的数据进行类型处理
     * @param value   单元格的值，
     *                value代表解析：BOOL的为0或1， ERROR的为内容值，FORMULA的为内容值，INLINESTR的为索引值需转换为内容值，
     *                SSTINDEX的为索引值需转换为内容值， NUMBER为内容值，DATE为内容值
     * @return
     */
    @SuppressWarnings("deprecation")
    public String getDataValue(String value) {
        String thisStr = null;
        switch (nextDataType) {
            // 这几个的顺序不能随便交换，交换了很可能会导致数据错误
            case BOOL: //布尔值
                thisStr=value;
                break;
            case ERROR: //错误
                thisStr = "\"ERROR:" + value.toString() + '"';
                break;
            case FORMULA: //公式
                thisStr = '"' + value.toString() + '"';
                break;
            case INLINESTR:
                XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
                thisStr = rtsi.toString();
                rtsi = null;
                break;
            case SSTINDEX: //字符串
                String sstIndex = value.toString();
                try {
                    int idx = Integer.parseInt(sstIndex);
                    XSSFRichTextString rtss = new XSSFRichTextString(sst.getEntryAt(idx));//根据idx索引值获取内容值
                    thisStr = rtss.toString();
                    rtss = null;
                } catch (NumberFormatException ex) {
                    thisStr = value.toString();
                }
                break;
            case NUMBER: //数字
                thisStr=value;
                thisStr = thisStr.replace("_", "").trim();
                break;
            case DATE: //日期
                thisStr=value;
                break;
            default:
                thisStr = value;
                break;
        }
        return thisStr;
    }

}
