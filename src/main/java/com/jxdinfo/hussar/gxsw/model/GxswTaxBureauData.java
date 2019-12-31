package com.jxdinfo.hussar.gxsw.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2019-10-25
 */
@TableName("gxsw_tax_bureau_data")
public class GxswTaxBureauData extends Model<GxswTaxBureauData> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;
    /**
     * 社会信用代码
     */
    @TableField("social_credit_code")
    private String socialCreditCode;
    /**
     * 纳税人名称
     */
    @TableField("taxpayer_name")
    private String taxpayerName;
    /**
     * 征收项目
     */
    @TableField("tax_item")
    private String taxItem;
    /**
     * 实缴金额
     */
    @TableField("tax_payment")
    private String taxPayment;
    @TableField("startDate")
    private String startDate;
    @TableField("endDate")
    private String endDate;
    /**
     * 征收子目
     */
    @TableField("tax_type")
    private String taxType;
    /**
     * 所属镇
     */
    @TableField("town")
    private String town;
    /**
     * 所属村
     */
    @TableField("village")
    private String village;
    @TableField("year")
    private String year;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
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

    public String getTaxPayment() {
        return taxPayment;
    }

    public void setTaxPayment(String taxPayment) {
        this.taxPayment = taxPayment;
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

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
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
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "GxswTaxBureauData{" +
                "id=" + id +
                ", socialCreditCode=" + socialCreditCode +
                ", taxpayerName=" + taxpayerName +
                ", taxItem=" + taxItem +
                ", taxPayment=" + taxPayment +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", taxType=" + taxType +
                ", town=" + town +
                ", village=" + village +
                ", year=" + year +
                "}";
    }
}
