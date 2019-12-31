package com.jxdinfo.hussar.gxsw.model;


/**
 * @author hasee
 */
public class GxswPayableInfo {
    /**
    * 参保人身份证号
    */
    private String identityCard;

    /**
    * 参保人姓名
    */
    private String taxpayerName;

    /**
     * 纳税类型
     */
    private String taxpayerItem;

    /**
    * 纳税人类型
    */
    private String taxpayerType;

    /**
    * 参保人所属街道/乡镇
    */
    private String taxpayerTown;

    /**
    * 参保人所属社区/村
    */
    private String taxpayerVillage;

    /**
    * 参保人年龄
    */
    private String taxpayerAge;

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

    public String getTaxpayerType() {
        return taxpayerType;
    }

    public void setTaxpayerType(String taxpayerType) {
        this.taxpayerType = taxpayerType;
    }

    public String getTaxpayerTown() {
        return taxpayerTown;
    }

    public void setTaxpayerTown(String taxpayerTown) {
        this.taxpayerTown = taxpayerTown;
    }

    public String getTaxpayerVillage() {
        return taxpayerVillage;
    }

    public void setTaxpayerVillage(String taxpayerVillage) {
        this.taxpayerVillage = taxpayerVillage;
    }

    public String getTaxpayerAge() {
        return taxpayerAge;
    }

    public void setTaxpayerAge(String taxpayerAge) {
        this.taxpayerAge = taxpayerAge;
    }

    public String getTaxpayerItem() {
        return taxpayerItem;
    }

    public void setTaxpayerItem(String taxpayerItem) {
        this.taxpayerItem = taxpayerItem;
    }

    @Override
    public String toString() {
        return "GxswPayableInfo{" +
                "identityCard='" + identityCard + '\'' +
                ", taxpayerName='" + taxpayerName + '\'' +
                ", taxpayerItem='" + taxpayerItem + '\'' +
                ", taxpayerType='" + taxpayerType + '\'' +
                ", taxpayerTown='" + taxpayerTown + '\'' +
                ", taxpayerVillage='" + taxpayerVillage + '\'' +
                ", taxpayerAge='" + taxpayerAge + '\'' +
                '}';
    }
}