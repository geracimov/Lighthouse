package ru.geracimov.otus.spring.lighthouse.managementserver.feign;


import com.pi4j.component.Component;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import ru.geracimov.otus.spring.lighthouse.managementserver.controller.dto.RegisterComponentDto;

import java.util.Collection;

@Headers("Accept: application/json")
public interface ComponentServiceProxy {

    @RequestLine("GET /component")
    <C extends Component> Collection<C> components();

    @RequestLine("POST /component/register")
    Object componentRegister(RegisterComponentDto dto);

    @RequestLine("PUT /component/{name}/toggle")
    void componentToggle(@Param("name") String name);

    @RequestLine("PUT /component/{name}/unregister")
    Object componentUnregister(@Param("name") String name);

    @RequestLine("GET /component/{name}/data")
    Object componentData(@Param("name") String name);

}
