package com.devin.data.analysis.admin.product.biz.impl;

import com.devin.data.analysis.admin.product.dto.DaEveryColorData;
import com.devin.data.analysis.admin.product.util.CQSSCUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAnalysisServiceImpl {
    public void analysisOpenCode(List<DaEveryColorData> dataList) {
        for (DaEveryColorData everyColorData : dataList) {
            int total = 0;
            total += everyColorData.getWanDigit().intValue() +
                    everyColorData.getQianDigit().intValue() +
                    everyColorData.getBaiDigit().intValue() +
                    everyColorData.getShiDigit().intValue() +
                    everyColorData.getGeDigit().intValue();
            everyColorData.setBigDeal(CQSSCUtil.isBig(total));
            everyColorData.setParity(CQSSCUtil.isOdd(total));
            everyColorData.setParity(CQSSCUtil.isVingtEtun(everyColorData.getGeDigit(),
                    everyColorData.getWanDigit()));

        }
    }
}
