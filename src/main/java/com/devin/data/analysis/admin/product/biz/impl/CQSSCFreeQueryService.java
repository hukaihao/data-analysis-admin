package com.devin.data.analysis.admin.product.biz.impl;

import com.devin.data.analysis.admin.core.component.BaseBatchExecutor;
import com.devin.data.analysis.admin.core.http.ServiceResponse;
import com.devin.data.analysis.admin.core.util.HttpClientUtil;
import com.devin.data.analysis.admin.core.util.ObjectMapperUtil;
import com.devin.data.analysis.admin.product.convertor.DaEveryColorDataConvertor;
import com.devin.data.analysis.admin.product.dto.CQSSCFreeResultDTO;
import com.devin.data.analysis.admin.product.dto.CQSSCResponse;
import com.devin.data.analysis.admin.product.dto.DaEveryColorData;
import com.devin.data.analysis.admin.product.integration.DaEveryColorDataMapper;
import com.devin.data.analysis.admin.product.util.CQSSCUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CQSSCFreeQueryService {
    private static final Logger logger = LoggerFactory.getLogger(CQSSCFreeQueryService.class);
    @Autowired
    private DaEveryColorDataMapper daEveryColorDataMapper;
    @Autowired
    private ProductAnalysisServiceImpl productAnalysisService;
    @Autowired
    private DataSource dataSource;

    /**
     * 调用开彩网接口，查询重庆时时彩数据
     *
     * @return
     */
    public List<CQSSCFreeResultDTO> queryTikectData() {
        List<CQSSCFreeResultDTO> dataist = new ArrayList<>();
        StringBuilder sb = new StringBuilder(CQSSCUtil.URI).append(CQSSCUtil.CODE);
        sb.append(CQSSCUtil.SEPARATOR).append(CQSSCUtil.ROWS).append(CQSSCUtil.FORMAT);
        String jsonBody = sb.toString();
        CQSSCResponse cQSSCResponse = null;
        logger.info("调用开彩网接口查询时时彩数据 jsonBody : {}", jsonBody);
        try {
            ServiceResponse serviceResponse = HttpClientUtil.postJson(sb.toString());
            logger.info("开彩网返回的数据 serviceResponse : {}", serviceResponse);
            Object content = serviceResponse.getData();
            if (content != null) {
                cQSSCResponse = ObjectMapperUtil.readValue((String) content, CQSSCResponse.class);
                dataist = cQSSCResponse.getData();
                logger.info("解析出的数据 cQSSCResponse : {}", cQSSCResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataist;
    }

    /**
     * 批量插入数据库
     *
     * @param paramList
     * @throws Exception
     */
    @Transactional
    public void batchInsertCQSSCData(List<DaEveryColorData> paramList) throws Exception {
        new BaseBatchExecutor<DaEveryColorData>(dataSource).execute(paramList, (DaEveryColorData cqssData) -> {
            daEveryColorDataMapper.insert(cqssData);
        });
    }

    /**
     * 转换数据格式，用于落库
     *
     * @param dataList
     * @return
     */
    public List<DaEveryColorData> convertDataForInsert(List<CQSSCFreeResultDTO> dataList) {
        List<DaEveryColorData> recordList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dataList)) {
            recordList = DaEveryColorDataConvertor.convertDataForInsert(dataList);
        }
        productAnalysisService.analysisOpenCode(recordList);
        return recordList;
    }
}
