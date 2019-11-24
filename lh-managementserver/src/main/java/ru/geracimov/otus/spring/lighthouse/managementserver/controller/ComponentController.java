package ru.geracimov.otus.spring.lighthouse.managementserver.controller;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ru.geracimov.otus.spring.lighthouse.managementserver.controller.dto.InstanceDto;
import ru.geracimov.otus.spring.lighthouse.managementserver.controller.dto.RegisterComponentDto;
import ru.geracimov.otus.spring.lighthouse.managementserver.feign.ComponentServiceProxy;
import ru.geracimov.otus.spring.lighthouse.managementserver.feign.FeignClientFactory;
import ru.geracimov.otus.spring.lighthouse.managementserver.service.RpiInstanceService;

@RestController
@RequiredArgsConstructor
public class ComponentController {
    @Value("${managementService.rpi.applicationName}")
    private String rpiApplicationName;
    private final FeignClientFactory factory;
    private final RpiInstanceService rpiService;

    @GetMapping("/rpi/{instanceId}/component")
    public Object getInstanceComponents(@PathVariable String instanceId) {
        val client = getClient(instanceId);
        return client.components();
    }

    @PostMapping("/rpi/{instanceId}/component/register")
    public Object registerComponent(@PathVariable String instanceId,
                                    @RequestBody RegisterComponentDto dto) {
        val client = getClient(instanceId);
        return client.componentRegister(dto);
    }

    @PutMapping("/rpi/{instanceId}/component/{name}/toggle")
    public void toggleComponent(@PathVariable String instanceId,
                                @PathVariable String name) {
        val client = getClient(instanceId);
        client.componentToggle(name);
    }

    @PostMapping("/rpi/{instanceId}/component/{name}/unregister")
    public Object unregisterComponent(@PathVariable String instanceId,
                                      @PathVariable String name) {
        val client = getClient(instanceId);
        return client.componentUnregister(name);
    }


    @GetMapping("/rpi/{instanceId}/component/{name}/data")
    public Object dataComponent(@PathVariable String instanceId,
                                @PathVariable String name) {
        val client = getClient(instanceId);
        return client.componentData(name);
    }

    private ComponentServiceProxy getClient(String instanceId) {
        val info = rpiService.rpiServices(instanceId);
        if (info.size() == 0) {
            throw new FeignException.NotFound("Rpi with instanceId " + instanceId + " not found", "".getBytes());
        }

        final InstanceDto instanceDto = info.get(0);
        return factory.getInstance(instanceDto.getAppName(), instanceDto.getHomePageUrl());
    }

}
