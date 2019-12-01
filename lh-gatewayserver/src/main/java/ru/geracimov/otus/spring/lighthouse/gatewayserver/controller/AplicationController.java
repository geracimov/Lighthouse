package ru.geracimov.otus.spring.lighthouse.gatewayserver.controller;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping
public class AplicationController {
    private final EurekaClient eurekaClient;
    @Value("${rpi.application.group:LighthouseApps}")
    private String rpiApplicationGroup;

    public AplicationController(@Qualifier("eurekaClient") EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    @GetMapping("/api")
    public ResponseEntity<Collection<ApplicationDto>> rpiApplications() {
        final Set<ApplicationDto> collect = eurekaClient.getApplications().getRegisteredApplications()
                                                        .stream()
                                                        .flatMap(a -> a.getInstancesAsIsFromEureka()
                                                                       .stream())
                                                        .filter(rpiUp())
                                                        .map(ApplicationDto::new)
                                                        .collect(Collectors.toSet());
        return ok(collect);
    }

    @GetMapping
    public Applications allpplications() {
        return eurekaClient.getApplications();
    }

    private Predicate<InstanceInfo> rpiUp() {
        return instanceInfo -> InstanceInfo.InstanceStatus.UP.equals(instanceInfo.getStatus())
                && rpiApplicationGroup.equalsIgnoreCase(instanceInfo.getAppGroupName());
    }

}
