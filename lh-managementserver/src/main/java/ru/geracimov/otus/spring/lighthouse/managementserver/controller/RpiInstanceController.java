package ru.geracimov.otus.spring.lighthouse.managementserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.geracimov.otus.spring.lighthouse.managementserver.controller.dto.InstanceDto;
import ru.geracimov.otus.spring.lighthouse.managementserver.service.RpiInstanceService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RpiInstanceController {
    @Value("${rpi.application.group}")
    private String rpiApplicationGroup;
    private final RpiInstanceService rpiService;

    @GetMapping("/rpi")
    public List<InstanceDto> rpiServices() {
        return rpiService.rpiServices();
    }

    @GetMapping("/rpi/{instanceId}")
    public List<InstanceDto> rpiServices(@PathVariable("instanceId") String instanceId) {
        return rpiService.rpiServices(instanceId);
    }

}
