package com.jtexplorer.util;

import com.jtexplorer.config.ParamStaticConfig;
import com.jtexplorer.entity.*;
import com.jtexplorer.entity.enums.RequestEnum;
import com.jtexplorer.redis.RedisCache;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * session 登录用户工具类
 *
 * @author 苏友朋
 * @date 2016/7/10
 */
@Component
public class SessionUtil {
    @Resource
    private RedisCache redisCache;


    /**
     * 将登录信息存储到session中
     *
     * @param session      会话
     * @param userInfo     登录人员信息
     * @param organization 登录运营商信息
     * @param role         登录角色信息
     */
    public static void setLoginInfoToSession(HttpSession session, UserInfo userInfo, Organization organization, Role role) {
        setLoginUser(userInfo, session);
        setLoginOrganization(organization, session);
        setLoginRole(role, session);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        setLoginRoles(roles, session);
    }

    /**
     * 将登录信息存储到session中
     *
     * @param session      会话
     * @param userInfo     登录人员信息
     * @param organization 登录运营商信息
     * @param role         登录人员全部角色信息
     */
    public static void setLoginInfoToSession(HttpSession session, UserInfo userInfo, Organization organization, List<Role> role) {
        setLoginUser(userInfo, session);
        setLoginOrganization(organization, session);
        setLoginRoles(role, session);
    }

    /**
     * 将登录信息存储到session中
     *
     * @param session      会话
     * @param userInfo     登录人员信息
     * @param organization 登录运营商信息
     * @param role         登录人员全部角色信息
     */
    public static void setLoginInfoToSession(HttpSession session, UserInfo userInfo, Organization organization, List<Role> role, List<Organization> organizations) {
        setLoginUser(userInfo, session);
        setLoginOrganization(organization, session);
        setLoginOrganizations(organizations, session);
        setLoginRoles(role, session);
    }

    /**
     * 将登录信息存储到session中
     *
     * @param session      会话
     * @param userInfo     登录人员信息
     * @param organization 登录运营商信息
     */
    public static void setLoginInfoToSession(HttpSession session, UserInfo userInfo, Organization organization) {
        setLoginUser(userInfo, session);
        setLoginOrganization(organization, session);
    }

    /**
     * 将登录信息取出
     *
     * @param session 会话
     */
    public static Map<String, Object> getLoginInfoBySession(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        map.put(LoginEnum.LOGIN_EMP.getCode(), getLoginUser(session));
        map.put(LoginEnum.LOGIN_Organization.getCode(), getLoginOrganization(session));
        map.put(LoginEnum.LOGIN_Organizations.getCode(), getLoginOrganizations(session));
        map.put(LoginEnum.LOGIN_ROLE.getCode(), getLoginRole(session));
        map.put(LoginEnum.LOGIN_ROLES.getCode(), getLoginRoles(session));
        return map;
    }


    /**
     * 获取登录用户信息
     *
     * @return JsonResult
     */
    public static UserInfo getLoginUser(HttpSession session) {
        return (UserInfo) session.getAttribute(LoginEnum.LOGIN_EMP.getCode());
    }

    /**
     * 获取登录用户绑定组织信息
     *
     * @return JsonResult
     */
    public static Organization getLoginOrganization(HttpSession session) {
        return (Organization) session.getAttribute(LoginEnum.LOGIN_Organization.getCode());
    }

    public static List<Organization> getLoginOrganizations(HttpSession session) {
        return (List<Organization>) session.getAttribute(LoginEnum.LOGIN_Organizations.getCode());
    }

    /**
     * 获取登录用户登录角色信息
     *
     * @return JsonResult
     */
    public static Role getLoginRole(HttpSession session) {
        return (Role) session.getAttribute(LoginEnum.LOGIN_ROLE.getCode());
    }

    /**
     * 获取登录用户全部角色信息
     *
     * @return JsonResult
     */
    public static List<Role> getLoginRoles(HttpSession session) {
        return (List<Role>) session.getAttribute(LoginEnum.LOGIN_ROLES.getCode());
    }

    /**
     * 获取登录用户权限
     *
     * @return JsonResult
     */
    public List<Permission> getLoginPer(HttpSession session, Long roleId) {
        // 获取登录机构
        Organization organization = getLoginOrganization(session);
        if (organization == null) {
            return null;
        }
        List<Permission> permissions = redisCache.getCacheObject(LoginEnum.LOGIN_PERMISSION.getCode() + "-" + roleId + "-" + organization.getOrgId() + "-" + session.getId(), ArrayList.class);
        return permissions;
    }

    public List<MiniRolePermission> getLoginMiniPer(HttpSession session, Long roleId) {
        // 获取登录机构
        Organization organization = getLoginOrganization(session);
        if (organization == null) {
            return null;
        }
        List<MiniRolePermission> permissions = redisCache.getCacheObject(LoginEnum.LOGIN_MINI_PERMISSION.getCode() + "-" + roleId + "-" + organization.getOrgId() + "-" + session.getId(), ArrayList.class);
        return permissions;
    }


    /**
     * 写入登录用户信息
     */
    public static void setLoginUser(UserInfo userInfo, HttpSession session) {
        session.setAttribute(LoginEnum.LOGIN_EMP.getCode(), userInfo);
    }

    /**
     * 写入登录用户绑定组织信息
     */
    public static void setLoginOrganization(Organization organization, HttpSession session) {
        session.setAttribute(LoginEnum.LOGIN_Organization.getCode(), organization);
    }

    public static void setLoginOrganizations(List<Organization> organizations, HttpSession session) {
        session.setAttribute(LoginEnum.LOGIN_Organizations.getCode(), organizations);
    }

    /**
     * 写入登录用户登录角色信息
     */
    public static void setLoginRole(Role role, HttpSession session) {
        session.setAttribute(LoginEnum.LOGIN_ROLE.getCode(), role);
    }

    /**
     * 写入登录用户全部角色信息
     */
    public static void setLoginRoles(List<Role> roles, HttpSession session) {
        session.setAttribute(LoginEnum.LOGIN_ROLES.getCode(), roles);
    }

    /**
     * 写入登录用户权限
     */
    public JsonResult setLoginPer(List<Permission> permissions, HttpSession session) {
        JsonResult jsonResult = new JsonResult();
        Long timeout = (Long) ParamStaticConfig.getWebappPathStatic(ParamStaticConfig.ConfigParamKeyEnum.redisTimeout);
        // 获取登录角色和登录机构
        Organization organization = getLoginOrganization(session);
        Role role = getLoginRole(session);
        if (organization == null) {
            jsonResult.buildFalseNew(RequestEnum.REQUEST_ERROR_NO_LOGIN, "请先选择登录机构");
            return jsonResult;
        }
        if (role == null) {
            jsonResult.buildFalseNew(RequestEnum.REQUEST_ERROR_NO_LOGIN, "请先选择登录角色");
            return jsonResult;
        }
        String key = LoginEnum.LOGIN_PERMISSION.getCode() + "-" + role.getRoleId() + "-" + organization.getOrgId() + "-" + session.getId();
        redisCache.setCacheObject(key, permissions);
        redisCache.expire(key, timeout);

        // 存储权限控制接口地址的全字符串，用于过滤器判断当前用户是否拥有该权限
        String urls = buildPerControlUrlString(permissions);
        redisCache.setCacheObject(key + "-urls", urls);
        redisCache.expire(key + "-urls", timeout);
        jsonResult.buildTrueNew(permissions.size(), permissions);
        return jsonResult;
    }

    private String buildPerControlUrlString(List<Permission> permissions) {
        StringBuilder sb = new StringBuilder();
        permissions.stream().forEach(v -> {
            if (StringUtil.isNotEmpty(v.getPermControlUrl())) {
                sb.append(v.getPermControlUrl()).append(";");
            }
            if (ListUtil.isNotEmpty(v.getChildren())) {
                sb.append(buildPerControlUrlString(v.getChildren()));
            }
        });
        return sb.toString();
    }

    /**
     * 写入登录用户小程序权限
     */
    public JsonResult setLoginMiniPer(List<MiniRolePermission> permissions, HttpSession session) {
        Long timeout = (Long) ParamStaticConfig.getWebappPathStatic(ParamStaticConfig.ConfigParamKeyEnum.redisTimeout);
        JsonResult jsonResult = new JsonResult();
        // 获取登录角色和登录机构
        Organization organization = getLoginOrganization(session);
        Role role = getLoginRole(session);
        if (organization == null) {
            jsonResult.buildFalseNew(RequestEnum.REQUEST_ERROR_NO_LOGIN, "请先选择登录机构");
            return jsonResult;
        }
        if (role == null) {
            jsonResult.buildFalseNew(RequestEnum.REQUEST_ERROR_NO_LOGIN, "请先选择登录角色");
            return jsonResult;
        }
        String key = LoginEnum.LOGIN_MINI_PERMISSION.getCode() + "-" + role.getRoleId() + "-" + organization.getOrgId() + "-" + session.getId();
        redisCache.setCacheObject(key, permissions);
        redisCache.expire(key, timeout);

        // 存储权限控制接口地址的全字符串，用于过滤器判断当前用户是否拥有该权限
        String urls = buildPerControlUrlStringMini(permissions);
        redisCache.setCacheObject(key + "-urls", urls);
        redisCache.expire(key + "-urls", timeout);
        jsonResult.buildTrueNew(permissions.size(), permissions);
        return jsonResult;
    }

    private String buildPerControlUrlStringMini(List<MiniRolePermission> permissions) {
        StringBuilder sb = new StringBuilder();
        permissions.stream().forEach(v -> {
            if (StringUtil.isNotEmpty(v.getMrpeControlUrl())) {
                sb.append(v.getMrpeControlUrl()).append(";");
            }
        });
        return sb.toString();
    }

    /**
     * 验证是否存在某url的权限
     *
     * @param url 权限地址
     * @return JsonResult
     */
    public JsonResult getLoginPer(String url, HttpSession session) {
        JsonResult jsonResult = new JsonResult();
        // 获取登录角色和登录机构
        Organization organization = getLoginOrganization(session);
        Role role = getLoginRole(session);
        if (organization == null) {
            jsonResult.buildFalseNew(RequestEnum.REQUEST_ERROR_NO_LOGIN, "请先选择登录机构");
            return jsonResult;
        }
        if (role == null) {
            jsonResult.buildFalseNew(RequestEnum.REQUEST_ERROR_NO_LOGIN, "请先选择登录角色");
            return jsonResult;
        }
        String urls = redisCache.getCacheObject(LoginEnum.LOGIN_PERMISSION.getCode() + "-" + role.getRoleId() + "-" + organization.getOrgId() + "-" + session.getId() + "-urls", String.class);
        if (StringUtil.isEmpty(urls)) {
            jsonResult.buildFalseNew(RequestEnum.REQUEST_ERROR_NO_LOGIN, "无权限，请尝试重新登陆");
            return jsonResult;
        }
        if (urls.contains(url)) {
            jsonResult.buildTrueNew();
        } else {
            jsonResult.buildFalseNew(RequestEnum.REQUEST_ERROR_LOGIN_NO_PERMISSION);
        }
        jsonResult.setSuccess(urls.contains(url));
        return jsonResult;
    }

    public JsonResult getLoginMiniPer(String url, HttpSession session) {
        JsonResult jsonResult = new JsonResult();
        // 获取登录角色和登录机构
        Organization organization = getLoginOrganization(session);
        Role role = getLoginRole(session);
        if (organization == null) {
            jsonResult.buildFalseNew(RequestEnum.REQUEST_ERROR_NO_LOGIN, "请先选择登录机构");
            return jsonResult;
        }
        if (role == null) {
            jsonResult.buildFalseNew(RequestEnum.REQUEST_ERROR_NO_LOGIN, "请先选择登录角色");
            return jsonResult;
        }
        String urls = redisCache.getCacheObject(LoginEnum.LOGIN_MINI_PERMISSION.getCode() + "-" + role.getRoleId() + "-" + organization.getOrgId() + "-" + session.getId() + "-urls", String.class);
        if (StringUtil.isEmpty(urls)) {
            jsonResult.buildFalseNew(RequestEnum.REQUEST_ERROR_NO_LOGIN, "无权限，请尝试重新登陆");
            return jsonResult;
        }
        if (urls.contains(url)) {
            jsonResult.buildTrueNew();
        } else {
            jsonResult.buildFalseNew(RequestEnum.REQUEST_ERROR_LOGIN_NO_PERMISSION);
        }
        jsonResult.setSuccess(urls.contains(url));
        return jsonResult;
    }

    /**
     * 清除登录信息
     */
    public static void removeLoginInfo(HttpSession session) {
        removeLoginUser(session);
        removeLoginOrganization(session);
        removeLoginOrganizations(session);
        removeLoginRole(session);
        removeLoginRoles(session);
    }

    /**
     * 清除登录用户信息
     */
    public static void removeLoginUser(HttpSession session) {
        session.removeAttribute(LoginEnum.LOGIN_EMP.getCode());
    }

    /**
     * 清除登录用户绑定组织信息
     */
    public static void removeLoginOrganization(HttpSession session) {
        session.removeAttribute(LoginEnum.LOGIN_Organization.getCode());
        removeLoginOrganizations(session);
    }

    public static void removeLoginOrganizations(HttpSession session) {
        session.removeAttribute(LoginEnum.LOGIN_Organizations.getCode());
    }

    /**
     * 清除登录用户登录角色信息
     */
    public static void removeLoginRole(HttpSession session) {
        session.removeAttribute(LoginEnum.LOGIN_ROLE.getCode());
    }

    /**
     * 清除登录用户全部角色信息
     */
    public static void removeLoginRoles(HttpSession session) {
        session.removeAttribute(LoginEnum.LOGIN_ROLES.getCode());
    }

    /**
     * 清除登录用户权限
     */
    public void removeLoginPer(HttpSession session) {
        // 获取登录角色和登录机构
        Organization organization = getLoginOrganization(session);
        Role role = getLoginRole(session);
        if (organization == null) {
            return;
        }
        if (role == null) {
            return;
        }
        redisCache.deleteObject(LoginEnum.LOGIN_PERMISSION.getCode() + "-" + role.getRoleId() + "-" + organization.getOrgId() + "-" + session.getId() + "-urls");
        redisCache.deleteObject(LoginEnum.LOGIN_PERMISSION.getCode() + "-" + role.getRoleId() + "-" + organization.getOrgId() + "-" + session.getId());
        redisCache.deleteObject(LoginEnum.LOGIN_MINI_PERMISSION.getCode() + "-" + role.getRoleId() + "-" + organization.getOrgId() + "-" + session.getId() + "-urls");
        redisCache.deleteObject(LoginEnum.LOGIN_MINI_PERMISSION.getCode() + "-" + role.getRoleId() + "-" + organization.getOrgId() + "-" + session.getId());
    }

    public static boolean verifyLoginUser(HttpSession session, JsonResult jsonResult) {
        UserInfo userInfo = getLoginUser(session);
        if (userInfo == null) {
            jsonResult.buildFalseNew(RequestEnum.REQUEST_ERROR_NO_LOGIN);
            return false;
        }
        if (StringUtil.isEmpty(userInfo.getUserId())) {
            jsonResult.buildFalseNew(RequestEnum.REQUEST_ERROR_NO_LOGIN);
            return false;
        }
        jsonResult.buildTrueNew();
        return true;
    }

    public static boolean verifyLoginOrganization(HttpSession session, JsonResult jsonResult) {
        Organization organization = getLoginOrganization(session);
        if (organization == null) {
            jsonResult.buildFalseNew(RequestEnum.REQUEST_ERROR_NO_LOGIN, "无登录运营商");
            return false;
        }
        if (StringUtil.isEmpty(organization.getOrgId())) {
            jsonResult.buildFalseNew(RequestEnum.REQUEST_ERROR_NO_LOGIN, "无登录运营商");
            return false;
        }
        jsonResult.buildTrueNew();
        return true;
    }

    public static boolean verifyLogin(HttpSession session, JsonResult jsonResult) {
        if (!verifyLoginUser(session, jsonResult)) {
            return false;
        }
        if (!verifyLoginOrganization(session, jsonResult)) {
            return false;
        }
        return true;
    }

    public enum LoginEnum {
        /**
         * 用于分辨
         */
        LOGIN_EMP("登录人信息", "adminUserInfo"),
        LOGIN_ROLE("登录人选择角色信息", "role"),
        LOGIN_ROLES("登录人全部角色信息", "roles"),
        LOGIN_Organization("登录人登录机构信息 ", "organization"),
        LOGIN_Organizations("登录人全部机构信息 ", "organizations"),
        LOGIN_PERMISSION("登录人选择角色绑定的权限信息 ", "permission"),
        LOGIN_MINI_PERMISSION("登录人选择角色绑定的小程序权限信息 ", "miniPermission"),
        ;

        /**
         * 说明
         */
        private String meaning;

        /**
         * 代码
         */
        private String code;

        public String getMeaning() {
            return meaning;
        }

        public String getCode() {
            return code;
        }

        LoginEnum(String meaning, String code) {
            this.meaning = meaning;
            this.code = code;
        }
    }
}
