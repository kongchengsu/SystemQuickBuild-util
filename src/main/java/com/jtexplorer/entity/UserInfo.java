package com.jtexplorer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author 空城
 * @since 2021-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 加密-盐
     */
    private String userSalt;

    /**
     * 性别（0未知，1男，2女）
     */
    private Integer userSex;

    /**
     * 昵称
     */
    private String userNickname;

    /**
     * 真实姓名
     */
    private String userRealName;

    /**
     * 手机号
     */
    private String userPhone;

    /**
     * 身份证
     */
    private String userIdNumber;

    /**
     * 用户头像
     */
    private String userHeadImg;

    /**
     * 国家
     */
    private String userCountry;

    /**
     * 省份
     */
    private String userProvince;

    /**
     * 城市
     */
    private String userCity;

    /**
     * 县区
     */
    private String userArea;

    /**
     * 城镇
     */
    private String userTown;

    /**
     * 详细地址
     */
    private String userAddress;

    /**
     * 行政单位id
     */
    private Long userDepartmentId;

    /**
     * 行政单位名称
     */
    private String userDepartmentName;

    /**
     * 行政职位
     */
    private String userDepartmentPosition;

    /**
     * 律师所在公司id
     */
    private Long userComapnyId;

    /**
     * 律师所在公司名称
     */
    private String userCompanyName;

    /**
     * 律师职位
     */
    private String userCompanyPosition;

    /**
     * 律师等级
     */
    private String userLawyerLevel;

    /**
     * 律师处理次数
     */
    private Integer userLawyerNumber;

    private Date userCreateTime;

    private Date userUpdateTime;

    /**
     * 用户状态（见枚举）
     */
    private Integer userStatus;

    /**
     * 是否删除（1是）
     */
    private Integer userDelete;

    /**
     * 上次登录角色id
     */
    private Long userLastRoleId;

    /**
     * 上次登录角色
     */
    private String userLastRoleName;

    /**
     * 上次登录机构id
     */
    private Long userLastOrgId;

    /**
     * 上次登录机构名称
     */
    private String userLastOrgName;

    /**
     * 上次登录时间
     */
    private Date userLastLoginTime;


}
