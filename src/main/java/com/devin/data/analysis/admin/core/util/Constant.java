package com.devin.data.analysis.admin.core.util;

public enum Constant {
    REQUEST_FAILED("0","REQUEST_FAILED" , "请求失败"),
    REQUEST_SUCCESS("1","REQUEST_SUCCESS", "请求成功");
    private String key;
    private String value;
    private String desc;

    Constant(String key, String value, String desc) {
        this.key = key;
        this.value = value;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? "" : value.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
