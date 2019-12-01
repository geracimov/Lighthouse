package ru.geracimov.otus.spring.lighthouse.componentserver.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.ComponentService;


@Component
@RequiredArgsConstructor
public class EventListenerExampleBean {
 private final ComponentService componentService;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        componentService.init();
    }

}
