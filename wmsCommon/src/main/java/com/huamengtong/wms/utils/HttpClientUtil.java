package com.huamengtong.wms.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLHandshakeException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpClientUtil {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final String CHARSET_UTF8 = "UTF-8";

    private static CloseableHttpClient httpClient = null ;
    private static CloseableHttpAsyncClient asyncHttpClient;
    private static RequestConfig requestConfig;

    // 静态初始化
    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(20);
        cm.setDefaultMaxPerRoute(20);
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
        PoolingNHttpClientConnectionManager ncm = null;
        try {
            ncm = new PoolingNHttpClientConnectionManager(new DefaultConnectingIOReactor());
        } catch (IOReactorException e) {
            LOG.error("init failed . exception messages: ", e);
        }
        // 如果初始化失败，程序退出
        assert ncm != null;

        ncm.setMaxTotal(20);
        ncm.setDefaultMaxPerRoute(20);
        asyncHttpClient = HttpAsyncClients.custom().setConnectionManager(ncm).build();
        asyncHttpClient.start();
        requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000)
                .setConnectionRequestTimeout(15000).build();
    }

    // 异常自动恢复处理, 使用HttpRequestRetryHandler接口实现请求的异常恢复,自定义的恢复策略
    public static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            // 设置恢复策略，在发生异常时候将自动重试3次
            if (executionCount >= 3) {
                // Do not retry if over max retry count
                return false;
            }
            if (exception instanceof NoHttpResponseException) {
                // Retry if the server dropped connection on us
                return true;
            }
            if (exception instanceof SSLHandshakeException) {
                // Do not retry on SSL handshake exception
                return false;
            }
            HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
            boolean idempotent = request instanceof HttpEntityEnclosingRequest;
            if (!idempotent) {
                // Retry if the request is considered idempotent
                return true;
            }
            return false;
        }
    };

    // 使用ResponseHandler接口处理响应，HttpClient使用ResponseHandler会自动管理连接的释放，解决了对连接的释放管理
    private static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
        // 自定义响应处理
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String charset = EntityUtils.getContentCharSet(entity) == null ? CHARSET_UTF8 :
                        EntityUtils.getContentCharSet(entity);
                return new String(EntityUtils.toByteArray(entity), charset);
                //return EntityUtils.toString(entity, "UTF-8");
            } else {
                return null;
            }
        }
    };

    /**
     * 发送 post请求
     * @param httpUrl 地址
     */
    public static String sendHttpPost(String httpUrl) {
        if (StringUtils.isEmpty(httpUrl)) {
            return null;
        }
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @param params 参数(格式:key1=value1&key2=value2)
     */
    public static String sendHttpPost(String httpUrl, String params) {
        if (StringUtils.isEmpty(httpUrl)) {
            return null;
        }
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            //设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @param maps 参数
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps) {
        if (StringUtils.isEmpty(httpUrl)) {
            return null;
        }
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求（带文件）
     * @param httpUrl 地址
     * @param maps 参数
     * @param fileLists 附件
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps, List<File> fileLists) {
        if (StringUtils.isEmpty(httpUrl)) {
            return null;
        }
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        for (String key : maps.keySet()) {
            meBuilder.addPart(key, new StringBody(maps.get(key), ContentType.TEXT_PLAIN));
        }
        for(File file : fileLists) {
            FileBody fileBody = new FileBody(file);
            meBuilder.addPart("files", fileBody);
        }
        HttpEntity reqEntity = meBuilder.build();
        httpPost.setEntity(reqEntity);
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求（头部信息）
     * @param url 请求URL
     * @param params 请求参数
     * @param header  头部参数
     * @return
     */
    public static String sendHttpPost(String url, Map<String, String> params, Map<String, String> header) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        HttpPost method = new HttpPost(url);
        method.setConfig(requestConfig);
        String responseStr = null;
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(getParamsList(params), CHARSET_UTF8);
            method.setEntity(formEntity);
            if (null != header) {
                for (Entry<String, String> entry : header.entrySet()) {
                    method.setHeader(entry.getKey(), entry.getValue());
                }
            }
            responseStr = httpClient.execute(method, responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("url:{} params:{} failed", url, params);
        }
        return responseStr;
    }




    /**
     *  发送Post请求 异步提交
     * @param url 请求地址
     * @param params 提交参数集, 键/值对
     * @param callback 回调函数
     */
    public static void sendAsyncHttpPost(String url, Map params, FutureCallback<HttpResponse> callback) {
        if (StringUtils.isEmpty(url)) {
            return;
        }
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(getParamsList(params), CHARSET_UTF8);
            final HttpPost httppost = new HttpPost(url);
            httppost.setEntity(formEntity);
            if (LOG.isInfoEnabled()) {
                LOG.info("HttpClient postAsync: url: {},params: {}", url, params);
            }
            httppost.setConfig(requestConfig);
            asyncHttpClient.execute(httppost, callback);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("url:{} params:{} failed", url, params);
        }
    }

    /**
     * 发送 get请求
     * @param httpUrl
     */
    public static String sendHttpGet(String httpUrl) {
        if (StringUtils.isEmpty(httpUrl)) {
            return null;
        }
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
        return sendHttpGet(httpGet);
    }

    /**
     * 发送 get请求
     * @param httpUrl 请求url
     * @param params 查询参数集, 键/值对
     * @return 响应消息
     */
    public static String sendHttpGet(String httpUrl, Map<String, String> params) {
        String responseContent = null;
        if (StringUtils.isEmpty(httpUrl)) {
            return null;
        }
        List qparams = getParamsList(params);
        if (CollectionUtils.isNotEmpty(qparams)) {
            String formatParams = URLEncodedUtils.format(qparams, CHARSET_UTF8);
            httpUrl = httpUrl.indexOf('?') < 0 ? httpUrl + "?" + formatParams : httpUrl.substring(0, httpUrl.indexOf('?') + 1) + formatParams;
        }
        HttpGet method = new HttpGet(httpUrl);
        method.setConfig(requestConfig);
        try {
            responseContent = httpClient.execute(method, responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("url:{} params:{} failed ", httpUrl, params);
        }
        return responseContent;
    }


    /**
     * 发送 Async HttpGet请求
     * @param url 提交地址
     * @param params 查询参数集, 键/值对
     * @param callback 回调函数
     */
    public static void sendAsyncHttpGet(String url, Map params, FutureCallback<HttpResponse> callback) {
        if (StringUtils.isEmpty(url)) {
            return;
        }
        try {
            List qparams = getParamsList(params);
            if (CollectionUtils.isNotEmpty(qparams)) {
                String formatParams = URLEncodedUtils.format(qparams, CHARSET_UTF8);
                url = url.indexOf('?') < 0 ? url + "?" + formatParams : url.substring(0, url.indexOf('?') + 1) + formatParams;
            }
            HttpGet method = new HttpGet(url);
            method.setConfig(requestConfig);
            asyncHttpClient.execute(method, callback);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("url:{} params:{} failed", url, params);
        }
    }



    /**
     * 发送 get请求Https
     * @param httpUrl
     */
    public static String sendHttpsGet(String httpUrl) {
        if (StringUtils.isEmpty(httpUrl)) {
            return null;
        }
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
        return sendHttpsGet(httpGet);
    }



    /**
     * 发送Post请求
     * @param httpPost
     * @return
     */
    private static String sendHttpPost(HttpPost httpPost) {
        String responseContent = null;
        try {
            httpPost.setConfig(requestConfig);
            // 执行请求
            responseContent = httpClient.execute(httpPost,responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseContent;
    }

    /**
     * 发送Get请求
     * @param httpGet
     * @return
             */
    private static String sendHttpGet(HttpGet httpGet ) {
        String responseContent = null;
        try {
            httpGet.setConfig(requestConfig);
            // 执行请求
            responseContent = httpClient.execute(httpGet,responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseContent;
    }

    /**
     * 发送Get请求Https
     * @param httpGet
     * @return
     */
    private static String sendHttpsGet(HttpGet httpGet) {
        String responseContent = null;
        try {
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(httpGet.getURI().toString()));
            DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
            httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();
            httpGet.setConfig(requestConfig);
            // 执行请求
            responseContent = httpClient.execute(httpGet,responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseContent;
    }

    /**
     * 将传入的键/值对参数转换为NameValuePair参数集
     *
     * @param paramsMap
     *            参数集, 键/值对
     * @return NameValuePair参数集
     */
    private static List getParamsList(Map<String, String> paramsMap) {
        if (MapUtils.isEmpty(paramsMap)) {
            return null;
        }
        List params = new ArrayList();
        for (Entry<String, String> entry : paramsMap.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return params;
    }

}