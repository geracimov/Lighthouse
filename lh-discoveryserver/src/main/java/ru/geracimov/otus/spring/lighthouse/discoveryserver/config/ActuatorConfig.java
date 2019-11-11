package ru.geracimov.otus.spring.lighthouse.discoveryserver.config;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorConfig {

    /**
     * HttpTraceFilter bean missing when using Eureka Server
     * https://github.com/spring-cloud/spring-cloud-netflix/issues/3528
     */
    @ConditionalOnMissingBean
    @Bean
    public HttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }

}
