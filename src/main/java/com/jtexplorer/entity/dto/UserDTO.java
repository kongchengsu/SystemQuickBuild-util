package com.jtexplorer.entity.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@ToString
public class UserDTO implements Serializable {

    private Long userId;

    private String userNo;

    private String userAccount;

    private String userPassword;

    private String userSalt;

    private String userToken;

    private String userRealName;

    private String userIdNumber;

    private String userPhoneNumber;

    private String userEmail;

    private String userSex;

    private Date userBirthday;

    private String userIsSpecial;

    private Date userDbCreateDate;

    private Date userDbUpdateDate;

    private String userImg;

    private String userEmpNumber;

    private Long userZoneId;

    private String userZoneName;

    private String userWechatNickName;

    private String userUninud;

    private String userName;

    private String userAge;

    private String userEncryptPassword;

    private String userWechatNumber;

    private String userCountry;

    private String userProvince;

    private String userCity;

    private String userDistrict;

    private String userAddress;

    private List roles;

    private List permissionDTOS;

    private List userRole;

    private Long roleId;

    private String roleName;
}
