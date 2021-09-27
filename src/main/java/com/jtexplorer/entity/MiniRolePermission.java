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
 * @author xu.wang
 * @since 2021-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MiniRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "mrpe_id", type = IdType.AUTO)
    private Long mrpeId;

    private Long mrpeRoleId;

    /**
     * 权限编码
     */
    private String mrpeCode;

    /**
     * 权限名称
     */
    private String mrpeName;

    private String mrpeControlUrl;

    private String mrpeIsLogin;

    /**
     * 预留字段
     */
    private String mrpeType;

    private Integer mrpeStatus;

    private Date mrpeDbCreateDate;


}
