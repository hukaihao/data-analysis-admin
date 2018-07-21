package com.devin.data.analysis.admin.product.biz.impl;

import com.devin.data.analysis.admin.product.dto.CQSSCFreeResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CQSSCQueryScheduleTask {

    private Logger logger = LoggerFactory.getLogger(CQSSCQueryScheduleTask.class);

    @Autowired
    private CQSSCFreeQueryService cQSSCFreeQueryService;

    @Scheduled(initialDelay = 60000, fixedRate = 5*60*1000)   //initialDelay = 1000表示延迟1000ms执行第一次任务
    public void queryCQSSCDataTask() {
       /* List<CQSSCFreeResultDTO> dataist = cQSSCFreeQueryService.queryTikectData();
        try{
            cQSSCFreeQueryService.batchInsertCQSSCData(
                    cQSSCFreeQueryService.convertDataForInsert(dataist));
        }catch(Exception e){
            logger.error("批量插入出错，exception : {}",e);
        }*/
    }
}
