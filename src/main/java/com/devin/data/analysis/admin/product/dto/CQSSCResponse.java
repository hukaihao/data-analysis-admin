package com.devin.data.analysis.admin.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

public class CQSSCResponse implements Serializable {
   // private static final long serialVersionUID = -4056616173117589297L;
    //行数
    @JsonProperty("rows")
    private int rows;
    //返回码
    @JsonProperty("code")
    private String code;
    //返回消息
    @JsonProperty("info")
    private String info;
    //数据行
    @JsonProperty("data")
    private ArrayList<CQSSCFreeResultDTO> data;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ArrayList<CQSSCFreeResultDTO> getData() {
        return data;
    }

    public void setData(ArrayList<CQSSCFreeResultDTO> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CQSSCResponse{" +
                "rows=" + rows +
                ", code='" + code + '\'' +
                ", info='" + info + '\'' +
                ", data=" + data +
                '}';
    }
}
