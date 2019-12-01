package ru.geracimov.otus.spring.lighthouse.gatewayserver.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulFilters {

    @Bean
    public PreFilter getAuthenticationFilter() {
        return new PreFilter();
    }

    @Bean
    public PostFilter getResponseAuditFilter() {
        return new PostFilter();
    }
//
//	@Bean
//	public RouteFilter getRouteFilter() {
//		return new RouteFilter();
//	}

}
