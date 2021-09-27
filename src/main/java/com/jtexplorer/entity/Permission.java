package com.jtexplorer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jtexplorer.entity.tree.MyTree;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author 空城
 * @since 2021-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Permission extends MyTree<Permission> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "perm_id", type = IdType.AUTO)
    private Long permId;

    /**
     * 父权限id
     */
    private Long permPid;

    /**
     * 权限路径
     */
    private String permPcode;

    /**
     * 权限名称，用于生成，title
     */
    private String permName;

    /**
     * 权限代码，唯一标识，用于生成path和name
     */
    private String permCode;

    /**
     * 权限类型，分三种：
     directory - 目录；
     menu - 菜单；
     button - 按钮；
     */
    private String permType;

    /**
     * 权限映射地址，用于生成映射地址
     */
    private String permUrl;

    /**
     * 权限状态，启用或者停用
     */
    private String permStatus;

    /**
     * 控制菜单显示或者不显示
     */
    private String permShow;

    /**
     * 排序
     */
    private Long permOrder;

    /**
     * 权限图标，类型为目录时必须有
     */
    private String permIcon;

    /**
     * 备注
     */
    private String permRemark;

    /**
     * 记录创建日期时间
     */
    private Date permDbCreateDate;

    /**
     * 记录最后一次修改日期时间
     */
    private Date permDbUpdateDate;

    /**
     * 标识该权限是否是登录可用,Y:是
     */
    private String permLogin;

    /**
     * 界面是否自动刷新
     */
    private String permKeppAlive;

    /**
     * 权限管控的地址
     */
    private String permControlUrl;

    /**
     * 角色是否拥有该权限
     */
    @TableField(exist = false)
    private boolean roleHave = false;


    @Override
    public void setIdAndPid() {
        setId(getPermId());
        setPId(getPermPid());
        setName(getPermName());
    }
}
