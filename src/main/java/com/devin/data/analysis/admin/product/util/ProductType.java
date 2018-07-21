package com.devin.data.analysis.admin.product.util;

public enum ProductType {
    CQSSC("01","CQSSC","重庆时时彩");

    private String code;
    private String enDesc;
    private String zhDesc;

    ProductType(String code,String enDesc,String zhDesc){
        this.code = code;
        this.enDesc = enDesc;
        this.zhDesc = zhDesc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEnDesc() {
        return enDesc;
    }

    public void setEnDesc(String enDesc) {
        this.enDesc = enDesc;
    }

    public String getZhDesc() {
        return zhDesc;
    }

    public void setZhDesc(String zhDesc) {
        this.zhDesc = zhDesc;
    }
}
