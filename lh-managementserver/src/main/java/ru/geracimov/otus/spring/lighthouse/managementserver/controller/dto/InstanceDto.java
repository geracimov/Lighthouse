package ru.geracimov.otus.spring.lighthouse.managementserver.controller.dto;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import lombok.Data;

@Data
public class InstanceDto {
    private final String appName;
    private final String instanceId;
    private final String homePageUrl;
    private final InstanceStatus status;
    private final String groupName;

    public InstanceDto(InstanceInfo instanceInfo) {
        this.appName = instanceInfo.getAppName();
        this.instanceId = instanceInfo.getInstanceId();
        this.homePageUrl = instanceInfo.getHomePageUrl();
        this.status = instanceInfo.getStatus();
        this.groupName = instanceInfo.getAppGroupName();
    }

}
