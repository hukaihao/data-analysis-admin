package com.devin.data.analysis.admin.product.web;

import com.devin.data.analysis.admin.core.util.ResponseUtil;
import com.devin.data.analysis.admin.file.CSVUtil;
import com.devin.data.analysis.admin.login.annotation.LoginAdmin;
import com.devin.data.analysis.admin.product.biz.impl.ProductDataUploadService;
import com.devin.data.analysis.admin.product.dto.EveryColorUploadDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/upload")
public class DataFileUploadController {

    private final Logger logger = LoggerFactory.getLogger(DataFileUploadController.class);

    @Autowired
    private ProductDataUploadService productDataUploadService;

    //处理文件上传
    @RequestMapping(value = "/cqssc", method = RequestMethod.POST)
    public @ResponseBody
    Object upload(@RequestParam("file") MultipartFile file,
                  HttpServletRequest request) {
        String reslut = "success";
        try {
            productDataUploadService.upload(file, EveryColorUploadDTO.class);
        } catch (Exception e) {
            reslut = productDataUploadService.getErrorFilePath(file);
            logger.error(" 文件解析出错 exception : {} ", e);
        }
        Map<String, String> data = new HashMap<>();
        data.put("result", reslut);
        return ResponseUtil.ok(data);
    }

    @RequestMapping(value = "/downloaderror", method = RequestMethod.GET)
    public Object download(@LoginAdmin String idDaAdmin, String fileName, HttpServletResponse response) throws Exception {
        BufferedInputStream bis = null;  //从文件中读取内容
        OutputStream bos = null; //向文件中写入内容
        logger.debug("fileName is {} ", fileName);
        if (idDaAdmin == null) {
            return ResponseUtil.unlogin();
        }

        //获得服务器上存放下载资源的地址
        String ctxPath = CSVUtil.ERROR_FILE_PATH;
        //获得下载文件全路径
        String downLoadPath = ctxPath + fileName;
        logger.debug("downLoadPath is {} ", downLoadPath);
        //如果文件不存在,退出
        File file = new File(downLoadPath);
        if (!file.exists()) {
            logger.debug("file is not exist ");
            return ResponseUtil.serious();
        }
        //获得文件大小
        long fileLength = file.length();
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");// ;charset=utf-8
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setHeader("Content-Length", String.valueOf(fileLength));
        try {
            bos = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            //定义文件读取缓冲区
            byte[] buff = new byte[2048];
            int bytesRead;
            //把下载资源写入到输出流
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
                bos.flush();
            }
        } catch (Exception e) {
            logger.error("错误文件下载出错");
            return ResponseUtil.serious();
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
        return null;
    }
}
