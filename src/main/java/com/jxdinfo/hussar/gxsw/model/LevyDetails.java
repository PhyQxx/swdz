package com.jxdinfo.hussar.gxsw.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.jxdinfo.hussar.util.bigdataexcel.MyWorkbook;

import java.util.Date;


/**
 * @author hasee
 */
@TableName("gxsw_levy_details")
public class LevyDetails {
    /**
    * 社会信用代码
    */
    @MyWorkbook(cell = "A")
    @TableId("credit_code")
    private String creditCode;

    /**
    * 纳税人姓名
    */
    @MyWorkbook(cell = "B")
    @TableId("taxpayer_name")
    private String taxpayerName;

    /**
    * 征收项目
    */
    @MyWorkbook(cell = "C")
    @TableId("taxpayer_name")
    private String taxItem;

    /**
    * 实缴金额
    */
    @MyWorkbook(cell = "D")
    @TableId("payment_amount")
    private String paymentAmount;

    /**
    * 税款所属期起
    */
    @MyWorkbook(cell = "E")
    @TableId("start_date")
    private String startDate;

    /**
    * 税款所属期止
    */
    @MyWorkbook(cell = "F")
    @TableId("end_date")
    private String endDate;


    /**
    * 征收子目
    */
    @MyWorkbook(cell = "G")
    @TableId("specific_item")
    private String specificItem;

    /**
     * 乡镇
     */
    @MyWorkbook(cell = "H")
    @TableId("town")
    private String town;
    /**
     * 村
     */
    @MyWorkbook(cell = "I")
    @TableId("village")
    private String village;

    /**
     * 导入年份
     */
    @TableId("year")
    private String year;

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getTaxpayerName() {
        return taxpayerName;
    }

    public void setTaxpayerName(String taxpayerName) {
        this.taxpayerName = taxpayerName;
    }

    public String getTaxItem() {
        return taxItem;
    }

    public void setTaxItem(String taxItem) {
        this.taxItem = taxItem;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSpecificItem() {
        return specificItem;
    }

    public void setSpecificItem(String specificItem) {
        this.specificItem = specificItem;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "LevyDetails{" +
                "creditCode='" + creditCode + '\'' +
                ", taxpayerName='" + taxpayerName + '\'' +
                ", taxItem='" + taxItem + '\'' +
                ", paymentAmount='" + paymentAmount + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", specificItem='" + specificItem + '\'' +
                ", town='" + town + '\'' +
                ", village='" + village + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}