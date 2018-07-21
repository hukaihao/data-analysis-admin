package com.devin.data.analysis.admin.product.biz.impl;

import com.devin.data.analysis.admin.product.dto.CQSSCFreeResultDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CQSSCFreeQueryServiceTest {
    @Autowired
    private CQSSCFreeQueryService cQSSCFreeQueryService;

    @Test
    public void queryTikectData() {
        List<CQSSCFreeResultDTO> dataist = cQSSCFreeQueryService.queryTikectData();
    }

    @Test
    public void testQueryAndInsert(){
        List<CQSSCFreeResultDTO> dataist = cQSSCFreeQueryService.queryTikectData();
        try{
            cQSSCFreeQueryService.batchInsertCQSSCData(
                    cQSSCFreeQueryService.convertDataForInsert(dataist));
        }catch(Exception e){
            e.printStackTrace();
        }
        while(true){

        }
    }
}