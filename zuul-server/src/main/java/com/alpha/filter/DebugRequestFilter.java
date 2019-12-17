package com.alpha.filter;

import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Enumeration;
import java.util.List;

/**
 * debug 调试 打印出入参和出参以及响应体
 */

@Component
public class DebugRequestFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(DebugRequestFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        HttpServletRequest req = (HttpServletRequest) RequestContext.getCurrentContext().getRequest();
        logger.info("Request: " + req.getScheme() + " " + req.getRemoteAddr() + ":" + req.getRemotePort());
        StringBuilder params = new StringBuilder("?");
        // 获取URL参数
        Enumeration<String> names = req.getParameterNames();
        if (req.getMethod().equals("GET")) {
            while (names.hasMoreElements()) {
                String name = (String) names.nextElement();
                params.append(name);
                params.append("=");
                params.append(req.getParameter(name));
                params.append("&");
            }
        }
        if (params.length() > 0) {
            params.delete(params.length() - 1, params.length());
        }
        logger.info("Request:: > " + req.getMethod() + " " + req.getRequestURI() + params + " " + req.getProtocol());
        Enumeration<String> headers = req.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = (String) headers.nextElement();
            String value = req.getHeader(name);
            logger.info("Request:: > " + name + ":" + value);
        }
        final RequestContext ctx = RequestContext.getCurrentContext();
        // 获取请求体参数
        if (!ctx.isChunkedRequestBody()) {
            ServletInputStream inp = null;
            try {
                inp = ctx.getRequest().getInputStream();
                String body = null;
                if (inp != null) {
                    body = getResponseStr(inp);
                    logger.info("Request:: > " + body);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<Pair<String, String>> headerList = RequestContext.getCurrentContext().getOriginResponseHeaders();
        for (Pair<String, String> pair : headerList) {
            logger.info("Response Header:: > " + pair.second());
        }
//        InputStream stream = RequestContext.getCurrentContext().getResponseDataStream();
//        try {
//            if (stream != null) {
//                String body = getResponseStr(stream);
//                logger.info("Response:: > " + body);
////                RequestContext.getCurrentContext().setResponseBody(body);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return null;
    }


    /**
     * 获取返回结果
     */
    private static String getResponseStr(InputStream inputStream) {
        String responseContent = null;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            responseContent = bufferedReader.readLine();
            while (responseContent != null) {
                stringBuffer.append(responseContent);
                responseContent = bufferedReader.readLine();
            }
            responseContent = stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseContent;
    }
}
