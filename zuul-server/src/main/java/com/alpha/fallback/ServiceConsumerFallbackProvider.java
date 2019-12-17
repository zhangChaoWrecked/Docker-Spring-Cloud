package com.alpha.fallback;


import com.alpha.util.JsonUtils;
import org.bouncycastle.asn1.ocsp.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Zuul网关熔断配置
 * 通过 ClientHttpResponse 构造回退的内容；
 * 通过 getStatusCode 返回响应的状态码；
 * 通过 getStatusText 返回响应状态码对应的文本；
 * 通过 getBody 返回回退的内容；
 * 通过getHeaders 返回响应的请求头信息
 */
@Component
public class ServiceConsumerFallbackProvider implements FallbackProvider {

    private Logger log = LoggerFactory.getLogger(ServiceConsumerFallbackProvider.class);

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return this.getStatusCode().value();
            }

            @Override
            public String getStatusText() throws IOException {
                return this.getStatusCode().getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                if (cause != null) {
                    log.error("", cause.getCause());
                }
                String data = new String(route + "服务内部错误");
                return new ByteArrayInputStream(JsonUtils.toJson(data).getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                MediaType mt = new MediaType("application", "json", Charset.forName("UTF-8"));
                headers.setContentType(mt);
                return headers;
            }
        };
    }
}
