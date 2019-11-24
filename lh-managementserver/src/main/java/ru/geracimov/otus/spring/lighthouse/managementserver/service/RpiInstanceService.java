package ru.geracimov.otus.spring.lighthouse.managementserver.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.geracimov.otus.spring.lighthouse.managementserver.controller.dto.InstanceDto;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RpiInstanceService {
    private final EurekaClient eurekaClient;
    @Value("${rpi.application.group}")
    private String rpiApplicationGroup;

    public List<InstanceDto> rpiServices() {
        return rpiFilterServices(all());
    }

    public List<InstanceDto> rpiServices(String instanceId) {
        return rpiFilterServices(byId(instanceId));
    }

    private List<InstanceDto> rpiFilterServices(Predicate<InstanceInfo> filter) {
        return eurekaClient.getApplications()
                           .getRegisteredApplications()
                           .stream()
                           .map(Application::getInstancesAsIsFromEureka)
                           .flatMap(Collection::stream)
                           .filter(info -> rpiApplicationGroup.equalsIgnoreCase(info.getAppGroupName()))
                           .filter(filter)
                           .map(InstanceDto::new)
                           .collect(Collectors.toList());
    }

    private Predicate<InstanceInfo> all() {
        return info -> true;
    }

    private Predicate<InstanceInfo> byId(@NonNull String instanceId) {
        return info -> instanceId.equalsIgnoreCase(info.getInstanceId());
    }

}
