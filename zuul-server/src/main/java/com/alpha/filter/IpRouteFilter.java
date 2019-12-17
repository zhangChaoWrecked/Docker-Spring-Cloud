package com.alpha.filter;



import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 过滤器示例
 * IP黑名单限制过滤器
 * <p>
 * 请求发过来首先到 pre 过滤器，再到routing 过滤器 ，最后 post过滤器，任何一个过滤器有异常 会进入 error 过滤器
 * pre    可以在请求被路由之前调用,适用于身份认证的场景，认证通过后再继续执行下面的流程
 * route  在路由请求时被调用,适用于灰度发布场景，在将要路由的时候可以做一些自定义的逻辑
 * post   在 route error 过滤器之后被调用 速种过滤器将请求路由到达具体的服务之后执行,适用于需要添加响应头，记录响应日志等应用场景
 * error  处理请求发生错误时被调用,在执行过程中发送错误时会进入 error 过滤器，可以用来统一记录错误信息
 * <p>
 * shouldFilter 是否执行该过滤器 true 为执行， false 为不执行，这个也可以利用配中心来实现，达到动态的开启和关闭过滤器
 * • filterType ：过滤器类型，可选值有 pre route post error
 * • filterOrder ：过滤器的执行顺序，数值越小，优先级越高
 * • run ：执行自己的业务逻辑，本段代码中是通过判断请求 IP 否在黑名单中，决定
 * 是否进行拦截的 判断条件成立之后，通过设置 ctx.setSendZuulResponse(false)，告诉 Zuul不需要将当前请求转发到后端的服务了
 * 通过 setResponseBody 返回数据给客户端
 *
 * */


@Component
public class IpRouteFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(IpRouteFilter.class);


    public IpRouteFilter() {
        super();
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Object success = ctx.get("isSuccess");
        return success == null ? true : Boolean.parseBoolean(success.toString());
    }

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }


    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        logger.info("IpRouteFilter--------------------------" + System.currentTimeMillis());
        return null;
    }
}
