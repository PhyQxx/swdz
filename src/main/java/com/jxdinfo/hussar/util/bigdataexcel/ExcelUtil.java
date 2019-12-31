package com.jxdinfo.hussar.util.bigdataexcel;

import dm.jdbc.util.StringUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.ZipPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @author wenqingkuan
 * @date 2019-10-21 11:24
 */
public class ExcelUtil {
    /**
     * 只拿取第一个sheet
     * @param callback
     * @param fileName
     * @param in
     */
    public static void readFirst(String fileName,InputStream in,Callback callback)  {
        XSSFReader reader = getXSSFReader(fileName,in);
        XMLReader parser = getXMLReader(callback,reader);
        Iterator<InputStream> sheetsData = getSheetsData(reader);
        parseFirst(sheetsData,parser);
    }

    public static void readAll(String fileName,InputStream in,Callback callback){
        XSSFReader reader = getXSSFReader(fileName,in);
        XMLReader parser = getXMLReader(callback, reader);
        Iterator<InputStream> sheetsData = getSheetsData(reader);
        parseAll(sheetsData,parser);
    }

    public static <T> T resultToObj(Map<String,String> result,Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field:fields){
                field.setAccessible(true);
                MyWorkbook workbook = field.getDeclaredAnnotation(MyWorkbook.class);
                if (workbook!=null){
                    String cell = workbook.cell();
                    String value = result.get(cell);
                    if (!StringUtil.isEmpty(value)){
                        value=value.trim();
                        if (field.getType()==String.class){
                            field.set(t,value);
                        }else if (field.getType()==Byte.class){
                            field.set(t,Byte.parseByte(value));
                        }else if (field.getType()==Short.class){
                            field.set(t,Short.parseShort(value));
                        }else if (field.getType()==Integer.class){
                            field.set(t,Integer.parseInt(value));
                        }else if (field.getType()==Long.class){
                            field.set(t,Long.parseLong(value));
                        }else if (field.getType()==Float.class){
                            field.set(t,Float.parseFloat(value));
                        }else if (field.getType()==Double.class){
                            field.set(t,Double.parseDouble(value));
                        }else if (field.getType()==Boolean.class){
                            field.set(t,value.equalsIgnoreCase("0")?false:true);
                        }else if (field.getType()==Character.class){
                            field.set(t,value.charAt(0));
                        }else if (field.getType()== BigDecimal.class){
                            field.set(t,new BigDecimal(value));
                        }else if (field.getType()== Date.class){
                            try {
                                double v = Double.parseDouble(value);
                                long m = (long) (v*24*60*60*1000);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                long n = Math.abs(sdf.parse("1900-00-30").getTime());
                                field.set(t,new Date(m-n));
                            } catch (Exception e) {
                                String format = workbook.format();
                                try {
                                    field.set(t,new SimpleDateFormat(format).parse(value));
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                    throw new RuntimeException(e1);
                                }
                            }
                        }
                    }
                }
                field.setAccessible(false);
            }
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void parseAll(Iterator<InputStream> sheetsData, XMLReader parser) {
        while (sheetsData.hasNext()){
            parse(sheetsData,parser);
        }
    }

    private static void parse(Iterator<InputStream> sheetsData, XMLReader parser){
        try(InputStream inputStream = sheetsData.next()) {
            parser.parse(new InputSource(inputStream));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void parseFirst(Iterator<InputStream> sheetsData,XMLReader parser){
        if (sheetsData.hasNext()){
            parse(sheetsData,parser);
        }
    }

    private static XSSFReader getXSSFReader(String fileName,InputStream in ){
        if (!fileName.endsWith(".xlsx")) {
            throw new RuntimeException("请使用word 2007的Excel格式,即xlsx格式");
        }
        XSSFReader reader = null;
        try {
            reader = new XSSFReader(ZipPackage.open(in));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (OpenXML4JException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return reader;
    }

    private static XMLReader getXMLReader(Callback callback, XSSFReader reader){
        XMLReader parser = null;
        try {
            parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        } catch (SAXException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        parser.setContentHandler(new ExcelXlsxHandle(callback,reader));
        return parser;
    }

    private static Iterator<InputStream> getSheetsData(XSSFReader reader){
        try {
            return reader.getSheetsData();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
