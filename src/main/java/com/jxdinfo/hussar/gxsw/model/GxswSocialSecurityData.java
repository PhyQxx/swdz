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
@TableName("gxsw_social_security_data")
public class GxswSocialSecurityData extends Model<GxswSocialSecurityData> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;
    /**
     * 社会保障号码
     */
    @TableField("social_security_number")
    private String socialSecurityNumber;
    /**
     * 姓名
     */
    @TableField("name")
    private String name;
    /**
     * 参保群体
     */
    @TableField("insured_group")
    private String insuredGroup;
    /**
     * 人员类别
     */
    @TableField("personnel_category")
    private String personnelCategory;
    /**
     * 申报类别
     */
    @TableField("declaration_category")
    private String declarationCategory;
    /**
     * 起使年份
     */
    @TableField("start_date")
    private String startDate;
    /**
     * 终止年份
     */
    @TableField("end_date")
    private String endDate;
    /**
     * 缴费时间
     */
    @TableField("payment_date")
    private String paymentDate;
    /**
     * 个人缴税金额
     */
    @TableField("personal_payment")
    private String personalPayment;
    /**
     * 所属乡镇
     */
    @TableField("town")
    private String town;
    /**
     * 所属村
     */
    @TableField("village")
    private String village;
    /**
     * 数据所属年份
     */
    @TableField("year")
    private String year;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInsuredGroup() {
        return insuredGroup;
    }

    public void setInsuredGroup(String insuredGroup) {
        this.insuredGroup = insuredGroup;
    }

    public String getPersonnelCategory() {
        return personnelCategory;
    }

    public void setPersonnelCategory(String personnelCategory) {
        this.personnelCategory = personnelCategory;
    }

    public String getDeclarationCategory() {
        return declarationCategory;
    }

    public void setDeclarationCategory(String declarationCategory) {
        this.declarationCategory = declarationCategory;
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

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPersonalPayment() {
        return personalPayment;
    }

    public void setPersonalPayment(String personalPayment) {
        this.personalPayment = personalPayment;
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
        return "GxswSocialSecurityData{" +
                "id=" + id +
                ", socialSecurityNumber=" + socialSecurityNumber +
                ", name=" + name +
                ", insuredGroup=" + insuredGroup +
                ", personnelCategory=" + personnelCategory +
                ", declarationCategory=" + declarationCategory +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", paymentDate=" + paymentDate +
                ", personalPayment=" + personalPayment +
                ", town=" + town +
                ", village=" + village +
                ", year=" + year +
                "}";
    }
}
