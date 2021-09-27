package com.jtexplorer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 空城
 * @since 2021-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "org_id", type = IdType.AUTO)
    private Long orgId;

    /**
     * 上级id
     */
    private Long orgPId;

    /**
     * 层级关系
     */
    private String orgPCode;

    /**
     * 学校编码
     */
    private String orgCode;

    /**
     * 学校名称
     */
    private String orgName;

    /**
     * 学校logo
     */
    private String orgLogo;

    /**
     * 学校等级（小初高-见枚举）
     */
    private Integer orgLevel;

    /**
     * 类型（公办、民办、行政单位、平台-见枚举）
     */
    private Integer orgType;

    private String orgCountry;

    /**
     * 省份
     */
    private String orgProvince;

    /**
     * 城市
     */
    private String orgCity;

    /**
     * 区域
     */
    private String orgArea;

    /**
     * 城镇
     */
    private String orgTown;

    /**
     * 详细地址
     */
    private String orgAddress;

    /**
     * 经度
     */
    private Double orgLongitude;

    /**
     * 纬度
     */
    private Double orgLatitude;

    /**
     *  是否删除（1是）
     */
    private Integer orgDelete;

    /**
     * 状态（见枚举）
     */
    private Integer orgStatus;

    private Date orgCreateTime;

    private Date orgUpdateTime;

    /**
     * 备注
     */
    private String orgRemark;


}
