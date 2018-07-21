package com.devin.data.analysis.admin.product.dto;

import com.devin.data.analysis.admin.core.date.DateUtil;
import com.devin.data.analysis.admin.file.annotation.ParseTemplate;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EveryColorUploadDTO {

    @ParseTemplate(description="产品代码",ruleRegex ="^\\d{9}$"
            ,ruleDesc = "产品代码应为9位数字")
    private String productCode;

    @ParseTemplate(description="开奖期数")
    private String lotteryPeriod;

    @ParseTemplate(description="开奖日期",formatter = DateUtil.DATA_TIME_FORMAT)
    private LocalDateTime lotteryDate;

    @ParseTemplate(description="万位")
    private Byte wanDigit;

    @ParseTemplate(description="千位")
    private Byte qianDigit;

    @ParseTemplate(description="百位")
    private Byte baiDigit;

    @ParseTemplate(description="十位")
    private Byte shiDigit;

    @ParseTemplate(description="个位")
    private Byte geDigit;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" [");
        sb.append("productCode=").append(productCode);
        sb.append(", lotteryPeriod=").append(lotteryPeriod);
        sb.append(", lotteryDate=").append(lotteryDate);
        sb.append(", wanDigit=").append(wanDigit);
        sb.append(", qianDigit=").append(qianDigit);
        sb.append(", baiDigit=").append(baiDigit);
        sb.append(", shiDigit=").append(shiDigit);
        sb.append(", geDigit=").append(geDigit);
        sb.append("]");
        return sb.toString();
    }
}