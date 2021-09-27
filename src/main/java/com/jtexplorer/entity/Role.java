package com.jtexplorer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * role_角色
 * </p>
 *
 * @author 苏
 * @since 2021-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色代码（admin/system）
     */
    private String roleCode;

    /**
     * 运营商id
     */
    private Long roleOperId;

    /**
     * 运营商名
     */
    private String roleOperName;

    /**
     * 备注
     */
    private String roleRemark;

    /**
     * 状态
     */
    private Integer roleStatus;

    /**
     * 数据创建时间
     */
    private Date roleDbCreateTime;

    /**
     * 数据最后一次修改时间
     */
    private Date roleDbUpdateTime;

    /**
     * 删除状态：1 已删除   0：未删除   role_delete_status
     */
    private Integer roleDeleteStatus;
    /**
     * 机构id，主要给登录信息使用
     */
    @TableField(exist = false)
    private Long orgId;


}
