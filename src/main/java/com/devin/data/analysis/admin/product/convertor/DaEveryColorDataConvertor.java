package com.devin.data.analysis.admin.product.convertor;

import com.devin.data.analysis.admin.core.date.DateUtil;
import com.devin.data.analysis.admin.product.dto.CQSSCFreeResultDTO;
import com.devin.data.analysis.admin.product.dto.DaEveryColorData;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DaEveryColorDataConvertor {

    public static List<DaEveryColorData> convertDataForInsert(List<CQSSCFreeResultDTO> dataList) {
        List<DaEveryColorData> recordList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dataList)) {
            for (CQSSCFreeResultDTO cqsscDTO : dataList) {
                DaEveryColorData everyColorData = new DaEveryColorData();
                everyColorData.setIdEveryColorData(cqsscDTO.getOpenTimestamp());
                everyColorData.setProductCode("987654321");
                everyColorData.setLotteryPeriod(cqsscDTO.getExpect());
                everyColorData.setLotteryDate(LocalDateTime.parse(cqsscDTO.getOpenTime(), DateUtil.DATA_TIME_FORMATTER));
                everyColorData.setWanDigit(cqsscDTO.getWanDigit());
                everyColorData.setQianDigit(cqsscDTO.getQianDigit());
                everyColorData.setBaiDigit(cqsscDTO.getBaiDigit());
                everyColorData.setShiDigit(cqsscDTO.getShiDigit());
                everyColorData.setGeDigit(cqsscDTO.getGeDigit());
                everyColorData.setCreatedDate(LocalDateTime.now());
                recordList.add(everyColorData);
            }
        }
        return recordList;
    }
}
