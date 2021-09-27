package com.jtexplorer.entity.enums;

/**
 * RequestEnum class
 *
 * @author 苏友朋
 * @date 2019/06/15 17:28
 */
public enum QueryTypeEnum {


    /**
     * 用于分辨查询是前端（公众号）还是后端（管理端）
     */
    QUERY_TYPE_FRONT("公众号端", "front"),
    QUERY_TYPE_ADMIN("管理端", "admin");

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

    QueryTypeEnum(String meaning, String code) {
        this.meaning = meaning;
        this.code = code;
    }
}
