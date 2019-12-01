package ru.geracimov.otus.spring.lighthouse.gatewayserver.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;

public class PostFilter extends ZuulFilter {

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        System.out.println("Response Audit in Post Filter->>>" + ctx.getResponse().getStatus());
        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER + 2;
    }

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

}
