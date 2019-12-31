package com.jxdinfo.hussar.gxsw.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.jxdinfo.hussar.util.bigdataexcel.MyWorkbook;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2019-10-17
 */

@TableName("gxsw_jcsj_info2")
public class GxswJcsjInfo extends Model<GxswJcsjInfo> {

    private static final long serialVersionUID = 1L;

    @MyWorkbook(cell = "A")
    @TableId("identity_card")
    private String identityCard;

    @MyWorkbook(cell = "B")
    @TableField("taxpayer_name")
    private String taxpayerName;

    @MyWorkbook(cell = "C")
    @TableField("taxpayer_item")
    private String taxpayerItem;

    @MyWorkbook(cell = "D")
    @TableField("taxpayer_money")
    private String taxpayerMoney;

    @MyWorkbook(cell = "E")
    @TableField("start_time")
    private String startTime;

    @MyWorkbook(cell = "F")
    @TableField("end_time")
    private String endTime;

    @MyWorkbook(cell = "G")
    @TableField("taxpayer_type")
    private String taxpayerType;

    @MyWorkbook(cell = "H")
    @TableField("town")
    private String town;

    @MyWorkbook(cell = "I")
    @TableField("village")
    private String village;


    @MyWorkbook(cell = "J")
    @TableField("age")
    private String age;


    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getTaxpayerName() {
        return taxpayerName;
    }

    public void setTaxpayerName(String taxpayerName) {
        this.taxpayerName = taxpayerName;
    }

    public String getTaxpayerItem() {
        return taxpayerItem;
    }

    public void setTaxpayerItem(String taxpayerItem) {
        this.taxpayerItem = taxpayerItem;
    }

    public String getTaxpayerMoney() {
        return taxpayerMoney;
    }

    public void setTaxpayerMoney(String taxpayerMoney) {
        this.taxpayerMoney = taxpayerMoney;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTaxpayerType() {
        return taxpayerType;
    }

    public void setTaxpayerType(String taxpayerType) {
        this.taxpayerType = taxpayerType;
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
    public String getAge() {
        return age;
    }


    @Override
    protected Serializable pkVal() {
        return this.identityCard;
    }


    @Override
    public String toString() {
        return "GxswJcsjInfo{" +
                "identityCard='" + identityCard + '\'' +
                ", taxpayerName='" + taxpayerName + '\'' +
                ", taxpayerItem='" + taxpayerItem + '\'' +
                ", taxpayerMoney='" + taxpayerMoney + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", taxpayerType='" + taxpayerType + '\'' +
                ", town='" + town + '\'' +
                ", village='" + village + '\'' +
                '}';
    }
}
