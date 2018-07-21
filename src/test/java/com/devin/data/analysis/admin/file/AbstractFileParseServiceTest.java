package com.devin.data.analysis.admin.file;

import com.devin.data.analysis.admin.product.dto.EveryColorUploadDTO;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.*;
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AbstractFileParseServiceTest {

    @Autowired
    private AbstractFileParseService abstractFileParseService;

    @Test
    public void upload() throws Exception {
        File file = new File("C:\\Users\\hp-430G5\\Desktop\\六合彩分析结果导出.xlsx");
        MultipartFile mulFile = new MockMultipartFile(
                "六合彩分析结果导出.xlsx", //文件名
                "六合彩分析结果导出.xlsx", //originalName 相当于上传文件在客户机上的文件名
                ContentType.APPLICATION_OCTET_STREAM.toString(), //文件类型
                new FileInputStream(file) //文件流
        );
        abstractFileParseService.upload(mulFile, EveryColorUploadDTO.class);

    }
}