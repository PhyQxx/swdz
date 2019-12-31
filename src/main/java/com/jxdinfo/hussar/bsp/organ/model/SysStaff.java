/*
 * 金现代轻骑兵V8开发平台 
 * SysStaff.java 
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
 * 人员信息表
 * </p>
 *
 * @author LiangDong
 * @since 2018-10-12
 */
@TableName("SYS_STAFF")
public class SysStaff extends Model<SysStaff> {

    private static final long serialVersionUID = 1L;

    /**
     * 人员表主键
     */
    @TableId(value = "STAFF_ID", type = IdType.UUID)
    private String staffId;
    /**
     * 人员对应的组织结构id（注：不是所属）
     */
    @TableField("STRU_ID")
    private String struId;
    /**
     * 姓名
     */
    @TableField("NAME")
    private String name;
    /**
     * 性别
     */
    @TableField("SEX")
    private String sex;
    /**
     * 出生日期
     */
    @TableField("BIRTHDAY")
    private String birthday;
    /**
     * 身份证号
     */
    @TableField("IDCARD")
    private String idcard;
    /**
     * 家庭住址
     */
    @TableField("ADDRESS")
    private String address;
    /**
     * 工号
     */
    @TableField("WORK_ID")
    private String workId;
    /**
     * 入职日期
     */
    @TableField("WORK_DATE")
    private String workDate;
    /**
     * 毕业时间
     */
    @TableField("GRADUATE_DATE")
    private String graduateDate;
    /**
     * 毕业院校
     */
    @TableField("GRADUATE_SCHOOL")
    private String graduateSchool;


    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStruId() {
        return struId;
    }

    public void setStruId(String struId) {
        this.struId = struId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getGraduateDate() {
        return graduateDate;
    }

    public void setGraduateDate(String graduateDate) {
        this.graduateDate = graduateDate;
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }


    @Override
    protected Serializable pkVal() {
        return this.staffId;
    }

    @Override
    public String toString() {
        return "SysStaff{" +
        "staffId=" + staffId +
        ", struId=" + struId +
        ", name=" + name +
        ", sex=" + sex +
        ", birthday=" + birthday +
        ", idcard=" + idcard +
        ", address=" + address +
        ", workId=" + workId +
        ", workDate=" + workDate +
                ", graduateSchool=" + graduateSchool +
        ", graduateDate=" + graduateDate +
        "}";
    }
}
