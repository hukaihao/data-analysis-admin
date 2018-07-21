package com.devin.data.analysis.admin.core.http;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientPool {
    private static final Logger log = LoggerFactory.getLogger(HttpClientPool.class);
    private static final int RETRY_COUNT = 3;
    private static final int MAX_CONN = 100;
    private static final int DEFAULT_MAX_CONN_ROUTE = 20;
    private static final int CONN_TIME_OUT = 3;
    private static final int CONN_REQUEST_TIME_OUT = 3;
    private static final int SOCKET_TIME_OUT = 5;
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final HttpRequestRetryHandler defaultRetryHandler = new HttpClientPool.CustomHttpRequestRetryHandler(3);
    private static PoolingHttpClientConnectionManager pcm = null;
    private static RequestConfig requestConfig = null;

    public HttpClientPool() {
    }

    public static CloseableHttpClient build() {
        log.info("*********   当前池中的连接状态：" + pcm.getTotalStats());
        return HttpClientBuilder.create().setConnectionManager(pcm).setRetryHandler(defaultRetryHandler).build();
    }

    public static CloseableHttpClient build(SSLConnectionSocketFactory sslsf) {
        return HttpClientBuilder.create().setSSLSocketFactory(sslsf).setRetryHandler(defaultRetryHandler).build();
    }

    public static CloseableHttpClient build(int retryCount) {
        log.info("*********   当前池中的连接状态：" + pcm.getTotalStats());
        return retryCount == 0 ? HttpClientBuilder.create().setConnectionManager(pcm).build() : HttpClientBuilder.create().setConnectionManager(pcm).setRetryHandler(new HttpClientPool.CustomHttpRequestRetryHandler(retryCount)).build();
    }

    public static RequestConfig buildConfig() {
        return requestConfig;
    }

    public static RequestConfig buildReadTimeoutConfig(int readTimeout) {
        return buildConfig(3, readTimeout, 3);
    }

    public static RequestConfig buildConnTimeoutConfig(int connTimeout) {
        return buildConfig(connTimeout, 5, 3);
    }

    public static RequestConfig buildConfig(int connTimeout, int readTimeout, int getHttpclientTimeout) {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connTimeout * 1000).setSocketTimeout(readTimeout * 1000).setConnectionRequestTimeout(getHttpclientTimeout * 1000).build();
        return requestConfig;
    }

    public static PoolStats getPoolStatus() {
        return pcm.getTotalStats();
    }

    static {
        SSLContext sslContext = null;

        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            sslContext = SSLContextBuilder.create().loadTrustMaterial(trustStore, new HttpClientPool.AnyTrustStrategy()).build();
        } catch (Exception var2) {
            throw new RuntimeException("初始化keystore失败", var2);
        }

        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory
                .getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory
                .getSocketFactory();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory> create().register("http", plainsf)
                .register("https", sslsf).build();

        log.info("*********  HttpClientPool  start init PoolingHttpClientConnectionManager!");
        pcm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        log.info("*********  HttpClientPool  config default ConnectionConfig!");
        pcm.setDefaultConnectionConfig(ConnectionConfig.custom().setCharset(Charset.forName("UTF-8")).build());
        pcm.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(3000).build());
        pcm.setMaxTotal(100);
        pcm.setDefaultMaxPerRoute(20);
        log.info("*********  HttpClientPoolManagerUtil initnal default requestConfig!");
        requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(5000).setConnectionRequestTimeout(3000).build();
    }

    static class CustomHttpRequestRetryHandler implements HttpRequestRetryHandler {
        private int count;

        public CustomHttpRequestRetryHandler(int count) {
            HttpClientPool.log.info("***********全局设置重试次数为：" + count);
            this.count = count;
        }

        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            HttpClientPool.log.debug("****************  handle 引发重试机制。  executionCount：" + executionCount + "    context:" + context + "    exception:" + exception + ":::::::" + exception.getMessage());
            HttpClientPool.log.debug("****************  正在确认是否需要重试......");
            if (executionCount >= this.count) {
                return false;
            } else if (!(exception instanceof NoHttpResponseException) && !(exception instanceof ConnectTimeoutException) && !(exception instanceof SocketTimeoutException)) {
                HttpClientPool.log.debug("****************  不符合重试条件，请求完成!");
                return false;
            } else {
                HttpClientPool.log.info("**************  符合条件，开始重新发起HttpClient请求！");
                return true;
            }
        }
    }

    static class TrustAnyHostnameVerifier implements HostnameVerifier {
        TrustAnyHostnameVerifier() {
        }

        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    static class AnyTrustStrategy implements TrustStrategy {
        AnyTrustStrategy() {
        }

        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            return true;
        }
    }
}
