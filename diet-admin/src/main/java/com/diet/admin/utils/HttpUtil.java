package com.diet.admin.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;

/**
 * @author LiuYu
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    //设置超时毫秒数
    private static final int CONNECT_TIMEOUT = 5000;
    //设置传输毫秒数
    private static final int SOCKET_TIMEOUT = 10000;
    //获取请求超时毫秒数
    private static final int REQUESTCONNECT_TIMEOUT = 3000;
    //最大连接数
    private static final int CONNECT_TOTAL = 200;
    //设置每个路由的基础连接数
    private static final int CONNECT_ROUTE = 20;
    //设置重用连接时间
    private static final int VALIDATE_TIME = 30000;

    private static final String RESPONSE_CONTENT = "请求失败";

    private static PoolingHttpClientConnectionManager pool = null;

    private static CloseableHttpClient client = null;


    private static void init() {
        if (pool == null) {
            pool = new PoolingHttpClientConnectionManager();
            // 整个连接池最大连接数
            pool.setMaxTotal(50);
            // 每路由最大连接数，默认值是2
            pool.setDefaultMaxPerRoute(5);
        }
    }

    private static CloseableHttpClient getHttpClient() {
        init();
        return HttpClients.custom().setConnectionManager(pool).build();
    }

    private static String res(HttpRequestBase method) {
//        HttpClientContext context = HttpClientContext.create();
        CloseableHttpResponse response = null;
        String content = RESPONSE_CONTENT;
        try {
            //执行GET/POST请求
            response = getHttpClient().execute(method);
            //获取响应实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                Charset charset = ContentType.getOrDefault(entity).getCharset();
                content = EntityUtils.toString(entity, charset);
                EntityUtils.consume(entity);
            }
        } catch (ConnectTimeoutException cte) {
            logger.error("请求连接超时，由于 " + cte.getLocalizedMessage());
            cte.printStackTrace();
        } catch (SocketTimeoutException ste) {
            logger.error("请求通信超时，由于 " + ste.getLocalizedMessage());
            ste.printStackTrace();
        } catch (ClientProtocolException cpe) {
            logger.error("协议错误（比如构造HttpGet对象时传入协议不对(将'http'写成'htp')or响应内容不符合），由于 " + cpe.getLocalizedMessage());
            cpe.printStackTrace();
        } catch (IOException ie) {
            logger.error("实体转换异常或者网络异常， 由于 " + ie.getLocalizedMessage());
            ie.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error("响应关闭异常， 由于 " + e.getLocalizedMessage());
            }
            if (method != null) {
                method.releaseConnection();
            }
        }
        return content;
    }

    /**
     * GET请求
     *
     * @param url 请求地址
     * @return
     */
    public static String get(String url) {
        HttpGet get = new HttpGet(url);
        return res(get);
    }

    /**
     * GET请求
     *
     * @param url    请求地址
     * @param cookie 请求cookie
     * @return
     */
    public static String get(String url, String cookie) {
        HttpGet get = new HttpGet(url);
        if (StringUtils.isNotBlank(cookie)) {
            get.addHeader("cookie", cookie);
        }
        return res(get);
    }

    /**
     * GET请求
     *
     * @param url    请求地址
     * @param cookie 请求cookie
     * @param header 请求header
     * @return
     */
    public static String get(String url, String header, String cookie) {
        HttpGet get = new HttpGet(url);
        if (StringUtils.isNotBlank(cookie)) {
            get.addHeader("cookie", cookie);
        }
        res(get);
        Header[] headers = get.getHeaders(header);
        return headers == null ? null : Arrays.toString(headers);
    }

    public static byte[] getAsByte(String url) {
        return get(url).getBytes();
    }

    /**
     * GET请求
     *
     * @param url 请求地址
     * @return
     */
    public static String getWithRealHeader(String url) {
        HttpGet get = new HttpGet(url);
        get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;");
        get.addHeader("Accept-Language", "zh-cn");
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
        get.addHeader("Keep-Alive", "300");
        get.addHeader("Connection", "Keep-Alive");
        get.addHeader("Cache-Control", "no-cache");
        return res(get);
    }

    /**
     * POST请求
     *
     * @param url    请求地址
     * @param header 请求header
     * @param cookie 请求cookie
     * @return
     */
    public static String post(String url, Map<String, String> header, String cookie) {
        HttpPost post = new HttpPost(url);
        if (null != header && !header.isEmpty()) {
            header.keySet().forEach(key -> {
                String value = header.get(key);
                if (value != null) {
                    post.addHeader(key, value);
                }
            });
        }
        if (StringUtils.isNotBlank(cookie)) {
            post.addHeader("cookie", cookie);
        }
        return res(post);
    }

    /**
     * 以application/json形式发出POST请求
     *
     * @param url    请求地址
     * @param params 请求参数,json格式
     * @return
     */
    public static String post(String url, String params) {
        HttpPost post = new HttpPost(url);
        if (StringUtils.isNotBlank(params)) {
            post.addHeader("Content-Type", "application/json");
        }
        post.setEntity(new StringEntity(params, ContentType.APPLICATION_JSON));
        return res(post);
    }

    /**
     * POST请求
     *
     * @param url    请求地址
     * @param params 请求参数，以A=a&B=b的形式拼装在url上
     * @param header 请求header
     * @param cookie 请求cookie
     * @return
     */
    public static String post(String url, Map<String, String> params, Map<String, String> header, String cookie) {
        HttpPost post = new HttpPost(url);
        StringBuffer reqEntity = new StringBuffer();
        if (null != params && !params.isEmpty()) {
            params.keySet().forEach(key -> {
                String value = params.get(key);
                if (value != null) {
                    try {
                        reqEntity.append(key).append("=").append(URLEncoder.encode(value, "utf-8")).append("&");
                    } catch (UnsupportedEncodingException e) {
                        logger.error("请求实体转换异常，不支持的字符集，由于 " + e.getLocalizedMessage());
                        e.printStackTrace();
                    }
                }
            });

            try {
                post.setEntity(new StringEntity(reqEntity.toString()));
            } catch (UnsupportedEncodingException e) {
                logger.error("请求设置实体异常，不支持的字符集， 由于 " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }

        if (null != header && !header.isEmpty()) {
            header.keySet().forEach(key -> {
                String value = header.get(key);
                if (value != null) {
                    post.addHeader(key, value);
                }
            });
        }

        if (StringUtils.isNotEmpty(cookie)) {
            post.addHeader("cookie", cookie);
        }
        return res(post);
    }
}