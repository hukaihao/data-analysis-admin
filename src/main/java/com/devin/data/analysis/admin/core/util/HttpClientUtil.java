package com.devin.data.analysis.admin.core.util;

import com.devin.data.analysis.admin.core.http.ServiceResponse;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.devin.data.analysis.admin.core.http.HttpClientPool.*;

public class HttpClientUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final Charset ENCODING = Charsets.UTF_8;
    private static final String DEFAULT_CONTENT_TYPE = "*/*";        // 请求的类型限制

    public static ServiceResponse postJson(String url, String content) {
        StringEntity stringEntity = new StringEntity(content,
                ContentType.create(DEFAULT_CONTENT_TYPE, ENCODING));    // 请求编码
        stringEntity.setContentType("application/json");
        return post(url, stringEntity);
    }
    public static ServiceResponse postJson(String url) {
        HttpPost httpPost = new HttpPost(url);
        return execute(httpPost);
    }

    public static ServiceResponse post(String url, String content) {
        StringEntity stringEntity = new StringEntity(content,
                ContentType.create(DEFAULT_CONTENT_TYPE, ENCODING));    // 请求编码
        return post(url, stringEntity);
    }


    public static ServiceResponse post(String url, StringEntity entity) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        return execute(httpPost);
    }

    public static ServiceResponse post(String url, String content, SSLConnectionSocketFactory sslsf) {
        StringEntity stringEntity = new StringEntity(content,
                ContentType.create(DEFAULT_CONTENT_TYPE, ENCODING));    // 请求编码
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(stringEntity);

        return execute(build(sslsf), httpPost);
    }


    public static ServiceResponse get(String uri) {
        return execute(new HttpGet(uri));
    }

    private static ServiceResponse execute(HttpRequestBase requestBase) {
        return execute(build(), requestBase, buildConfig());
    }

    private static ServiceResponse execute(CloseableHttpClient client, HttpRequestBase requestBase) {
        return execute(client, requestBase, buildConfig());
    }

    private static ServiceResponse execute(CloseableHttpClient client,
                                           HttpRequestBase requestBase,
                                           RequestConfig config) {
        log.info("###### 请求信息：" + requestBase);
        ServiceResponse serviceResponse = null;
        CloseableHttpResponse response = null;
        try {
            requestBase.setConfig(config);            // 使用默认配置
            response = client.execute(requestBase);        // 执行请求

            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                serviceResponse = new ServiceResponse(true,
                        String.valueOf(statusCode),
                        IOUtils.toString(response.getEntity().getContent(), ENCODING.name()));
            } else {
                serviceResponse = new ServiceResponse("请求第三方支付失败，请稍后再试！");
            }

            log.debug("###### 请求结果：" + serviceResponse + " statusCode: " + statusCode);
        } catch (Exception e) {
            log.error("content : " + requestBase.toString());
            log.error("HttpClientPoolManager 调用出错!  错误:{0}", e.getMessage());

            serviceResponse = new ServiceResponse(e.getMessage());
        } finally {
            if (null != response) {
                try {
                    response.close();        // 关闭response 释放资源
                    log.info("############   释放资源，当前线程池占用情况为：" + getPoolStatus());
                } catch (IOException e) {
                    log.error("*********** 关闭response，释放资源失败！" + e.getMessage());
                }
            }
        }
        return serviceResponse;
    }
}