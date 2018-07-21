package com.devin.data.analysis.admin.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 重庆时时彩免费接口查询结果
 */
public class CQSSCFreeResultDTO implements Serializable {
    private static final long serialVersionUID = 5362010824267577040L;
    private static final String SEPARATOR = ",";
    //开奖期号
    @JsonProperty("expect")
    private String expect;
    //开奖号码
    @JsonProperty("opencode")
    private String openCode;
    //开奖时间
    @JsonProperty("opentime")
    private String openTime;
    //开奖时间-
    @JsonProperty("opentimestamp")
    private String openTimestamp;

    private List<String> openCodeList;

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getOpenCode() {
        return openCode;
    }

    public void setOpenCode(String openCode) {
        this.openCode = openCode;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getOpenTimestamp() {
        return openTimestamp;
    }

    public void setOpenTimestamp(String openTimestamp) {
        this.openTimestamp = openTimestamp;
    }

    public List<String> getOpenCodeList() {
        if (CollectionUtils.isEmpty(openCodeList)) {
            setOpenCodeList();
        }
        return openCodeList;
    }

    public void setOpenCodeList() {
        List<String> openCodeList = new ArrayList<>();
        if (!StringUtils.isEmpty(openCode)) {
            openCodeList = Stream.of(openCode.split(SEPARATOR)).collect(Collectors.toList());
        }
        this.openCodeList = openCodeList;
    }

    public Byte getWanDigit() {
        getOpenCodeList();
        return Byte.parseByte(openCodeList.get(0));
    }

    public Byte getQianDigit() {
        getOpenCodeList();
        return Byte.parseByte(openCodeList.get(1));
    }

    public Byte getBaiDigit() {
        getOpenCodeList();
        return Byte.parseByte(openCodeList.get(2));
    }

    public Byte getShiDigit() {
        getOpenCodeList();
        return Byte.parseByte(openCodeList.get(3));
    }

    public Byte getGeDigit() {
        return Byte.parseByte(openCodeList.get(4));
    }

    @Override
    public String toString() {
        return "CQSSCFreeResultDTO [expect=" + expect + ", openCode=" + openCode + ", openTime=" + openTime + ", openTimestamp="
                + openTimestamp + "]";
    }

}
