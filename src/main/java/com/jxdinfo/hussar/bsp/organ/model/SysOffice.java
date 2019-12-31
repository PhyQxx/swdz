/*
 * 金现代轻骑兵V8开发平台 
 * SysOffice.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.bsp.organ.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 组织机构扩展表
 * </p>
 *
 * @author 
 * @since 2019-01-17
 */
@TableName("SYS_OFFICE")
public class SysOffice extends Model<SysOffice> {

    private static final long serialVersionUID = 1L;

    /**
     * 组织机构id
     */
    @TableId(value = "OFFICE_ID",type = IdType.UUID)
    private String officeId;
    /**
     * 对应的组织结构id（注：不是所属）
     */
    @TableField("STRU_ID")
    private String struId;
    /**
     * 组织机构描述
     */
    @TableField("OFFICE_ALIAS")
    private String officeAlias;
    /**
     * 联系地址
     */
    @TableField("OFFICE_ADDRESS")
    private String officeAddress;


    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getStruId() {
        return struId;
    }

    public void setStruId(String struId) {
        this.struId = struId;
    }

    public String getOfficeAlias() {
        return officeAlias;
    }

    public void setOfficeAlias(String officeAlias) {
        this.officeAlias = officeAlias;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    @Override
    protected Serializable pkVal() {
        return this.officeId;
    }

    @Override
    public String toString() {
        return "SysOffice{" +
        "officeId=" + officeId +
        ", struId=" + struId +
        ", officeAlias=" + officeAlias +
        ", officeAddress=" + officeAddress +
        "}";
    }
}
