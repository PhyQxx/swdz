package com.jxdinfo.hussar.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public class PlDao {

	public static HSSFCellStyle getHeadStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)11);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setBold(true);
		cellStyle.setFont(font);
//		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		
//		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
		
//		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		 cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
		
		//设置单元格背景色
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		 
		cellStyle.setLocked(true);
		return cellStyle;
	}
	/**
	 *
	 * 表格样式——正常黑色居中
	 */
	public static HSSFCellStyle createEditStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(false);
		cellStyle.setLocked(true);
//		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN)
//		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		
//		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

		return cellStyle;
	}

	/**
	 * @description 表格样式
	 * @param workbook
	 * @author  wh
	 * @date 2013-3-5
	 */
	public static HSSFCellStyle getCellYellowStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)11);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setBold(true);
		cellStyle.setFont(font);
//		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
		
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
		
		cellStyle.setLocked(true);
		return cellStyle;
	}

	/**
	 * @description 表格样式
	 * @param workbook
	 * @author  wh
	 * @date 2013-3-5
	 */
	public static HSSFCellStyle getCellGreenStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)11);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setBold(true);
		cellStyle.setFont(font);
		HSSFPalette customPalette = workbook.getCustomPalette();
		customPalette.setColorAtIndex(HSSFColorPredefined.SEA_GREEN.getIndex(),(byte) 0, (byte) 187, (byte) 187);
//		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cellStyle.setFillForegroundColor(HSSFColorPredefined.SEA_GREEN.getIndex());
		cellStyle.setLocked(true);
		return cellStyle;
	}

	/**
	 * @description 表格样式
	 * @param workbook
	 * @author  wh
	 * @date 2013-3-5
	 */
	public static HSSFCellStyle getCellCoralStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)11);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setBold(true);
		cellStyle.setFont(font);
		HSSFPalette customPalette = workbook.getCustomPalette();
		customPalette.setColorAtIndex(HSSFColorPredefined.CORAL.getIndex(),(byte) 253, (byte) 233, (byte) 217);
//		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cellStyle.setFillForegroundColor(HSSFColorPredefined.CORAL.getIndex());
		cellStyle.setLocked(true);
		return cellStyle;
	}

	/**
	 * @description 表格样式
	 * @param workbook
	 * @author  wh
	 * @date 2013-3-5
	 */
	public static HSSFCellStyle getCellOrangeStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)11);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setBold(true);
		cellStyle.setFont(font);
		HSSFPalette customPalette = workbook.getCustomPalette();
		customPalette.setColorAtIndex(HSSFColorPredefined.ORANGE.getIndex(),(byte) 226, (byte) 107, (byte) 10);
//		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cellStyle.setFillForegroundColor(HSSFColorPredefined.ORANGE.getIndex());
		cellStyle.setLocked(true);
		return cellStyle;
	}

	/**
	 * @description 表格样式
	 * @param workbook
	 * @author  wh
	 * @date 2013-3-5
	 */
	public static HSSFCellStyle getCellOrchidStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)11);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setBold(true);
		font.setColor(HSSFColorPredefined.WHITE.getIndex());
		cellStyle.setFont(font);
		HSSFPalette customPalette = workbook.getCustomPalette();
		customPalette.setColorAtIndex(HSSFColorPredefined.ORCHID.getIndex(),(byte) 112, (byte) 48, (byte) 160);
//		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cellStyle.setFillForegroundColor(HSSFColorPredefined.ORCHID.getIndex());
		cellStyle.setLocked(true);
		return cellStyle;
	}

	/**
	 * @description 表格样式
	 * @param workbook
	 * @author  wh
	 * @date 2013-3-5
	 */
	public static HSSFCellStyle getCellAquaStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)11);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setBold(true);
		cellStyle.setFont(font);
		HSSFPalette customPalette = workbook.getCustomPalette();
		customPalette.setColorAtIndex(HSSFColorPredefined.AQUA.getIndex(),(byte) 183, (byte) 222, (byte) 232);
//		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cellStyle.setFillForegroundColor(HSSFColorPredefined.AQUA.getIndex());
		cellStyle.setLocked(true);
		return cellStyle;
	}

	/**
	 * @description 表格样式
	 * @param workbook
	 * @author  wh
	 * @date 2013-3-5
	 */
	public static HSSFCellStyle getCellGreyStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)11);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setBold(true);
		cellStyle.setFont(font);
		HSSFPalette customPalette = workbook.getCustomPalette();
		customPalette.setColorAtIndex(HSSFColorPredefined.GREY_50_PERCENT.getIndex(),(byte) 196, (byte) 215, (byte) 155);
//		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_50_PERCENT.getIndex());
		cellStyle.setLocked(true);
		return cellStyle;
	}

	/**
	 * @description 表头样式
	 * @param workbook
	 * @author  wh
	 * @date 2017-1-13
	 */
	public static HSSFCellStyle getOneHeadStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)20);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setBold(true);
		cellStyle.setFont(font);
//		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
		
		cellStyle.setLocked(true);
		return cellStyle;
	}

	public static HSSFCellStyle getTitleStyle(HSSFWorkbook workbook) {
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)20);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setBold(true);
		titleStyle.setFont(font);
//		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		titleStyle.setBorderBottom(BorderStyle.THIN);
		titleStyle.setBorderLeft(BorderStyle.THIN);
		titleStyle.setBorderTop(BorderStyle.THIN);
		titleStyle.setBorderRight(BorderStyle.THIN);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
		
		titleStyle.setLocked(true);
		return titleStyle;
	}

	/**
	 * @description 导出表中数字的格式(据中)
	 * @param workbook
	 * @return
	 * @author  wh
	 * @date 2013-3-25
	 */
	public static HSSFCellStyle getNumCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle contentStyle = workbook.createCellStyle();
		HSSFFont conFont = workbook.createFont();
//		contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		contentStyle.setBorderBottom(BorderStyle.THIN);
		contentStyle.setBorderLeft(BorderStyle.THIN);
		contentStyle.setBorderTop(BorderStyle.THIN);
		contentStyle.setBorderRight(BorderStyle.THIN);
		contentStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
		contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
		
		contentStyle.setLocked(false);
		contentStyle.setWrapText(true);
//		conFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		conFont.setBold(false);
		conFont.setFontHeightInPoints((short) 10);
		contentStyle.setFont(conFont);
		return contentStyle;
	}

	/**
	 * @description 表格内容的样式
	 * @param workbook
	 * @return
	 * @author  wh
	 * @date 2013-3-5
	 */
	public static HSSFCellStyle getContentStyle(HSSFWorkbook workbook) {
		//设置表格体样式
		HSSFCellStyle contentStyle = workbook.createCellStyle();
		HSSFFont conFont = workbook.createFont();
		//设置边框
//		contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		//设置居中
//		contentStyle.setAlignment(HSSFCellStyle.ALIGN_GENERAL);
//		contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		contentStyle.setBorderBottom(BorderStyle.THIN);
		contentStyle.setBorderLeft(BorderStyle.THIN);
		contentStyle.setBorderTop(BorderStyle.THIN);
		contentStyle.setBorderRight(BorderStyle.THIN);
		contentStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
		contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
		
		//		//设定前景色背景色
		//		contentStyle.setFillPattern((short)1);
		//		contentStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		//设定格锁定
		contentStyle.setLocked(false);
		//设定自动换行
		contentStyle.setWrapText(true);
		//设置字体为普通11号字
//		conFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		conFont.setBold(false);
		conFont.setFontHeightInPoints((short) 11);
		contentStyle.setFont(conFont);
		return contentStyle;
	}

	/**
	 * @description 行头的样式
	 * @param workbook
	 * @return
	 * @author  wh
	 * @date 2013-3-17
	 */
	public static HSSFCellStyle getRowHeadStyle(HSSFWorkbook workbook) {
		//设置表格体样式
		HSSFCellStyle contentStyle = workbook.createCellStyle();
		HSSFFont conFont = workbook.createFont();
		//设置边框
//		contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		contentStyle.setAlignment(HSSFCellStyle.ALIGN_GENERAL);
//		contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		contentStyle.setBorderBottom(BorderStyle.THIN);
		contentStyle.setBorderLeft(BorderStyle.THIN);
		contentStyle.setBorderTop(BorderStyle.THIN);
		contentStyle.setBorderRight(BorderStyle.THIN);
		contentStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
		contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
		
		contentStyle.setLocked(true);
		contentStyle.setWrapText(true);
//		conFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		conFont.setBold(true);
		conFont.setFontHeightInPoints((short) 10);
		contentStyle.setFont(conFont);
		return contentStyle;
	}

	/**
	 * @description 序号样式
	 * @param workbook
	 * @return
	 * @author  wh
	 * @date 2013-3-17
	 */
	public static HSSFCellStyle getRowSortStyle(HSSFWorkbook workbook) {
		//设置表格体样式
		HSSFCellStyle contentStyle = workbook.createCellStyle();
		HSSFFont conFont = workbook.createFont();
		//设置边框
//		contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		//设置居中
//		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		contentStyle.setBorderBottom(BorderStyle.THIN);
		contentStyle.setBorderLeft(BorderStyle.THIN);
		contentStyle.setBorderTop(BorderStyle.THIN);
		contentStyle.setBorderRight(BorderStyle.THIN);
		contentStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
		contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
		
		//设定格锁定
		contentStyle.setLocked(true);
		//设定自动换行
		contentStyle.setWrapText(true);
		//设置字体为普通11号字
//		conFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		conFont.setBold(false);
		conFont.setFontHeightInPoints((short) 11);
		contentStyle.setFont(conFont);
		return contentStyle;
	}


}
