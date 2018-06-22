package com.devin.data.analysis.admin.login;

import com.devin.data.analysis.admin.login.integration.StatMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Map;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StatMapperTest {

    @Autowired
    private StatMapper statMapper;

    @Test
    public void testUser() {
        List<Map> result = statMapper.statUser();
        for (Map m : result) {
            m.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
        }
    }


}
