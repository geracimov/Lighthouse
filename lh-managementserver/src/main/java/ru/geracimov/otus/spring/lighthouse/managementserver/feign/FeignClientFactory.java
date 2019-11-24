package ru.geracimov.otus.spring.lighthouse.managementserver.feign;

import feign.Feign;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Import(FeignClientsConfiguration.class)
public class FeignClientFactory {
    private final Map<Integer, ComponentServiceProxy> clients = new HashMap<>();

    private final Encoder encoder;
    private final Decoder decoder;

    public ComponentServiceProxy getInstance(@NonNull String clientName, @NonNull String url) {
        int hsh = hash(clientName, url);
        ComponentServiceProxy client = clients.get(hsh);
        if (client == null) {
            client = createNewClient(clientName, url);
            clients.put(hsh, client);
        }
        return client;
    }

    private ComponentServiceProxy createNewClient(String clientName, String url) {
        Class<ComponentServiceProxy> clazz = ComponentServiceProxy.class;
        Target<ComponentServiceProxy> target = new Target.HardCodedTarget<>(clazz, clientName, url);
        return Feign.builder()
                    .encoder(encoder)
                    .decoder(decoder)
                    .target(target);
    }

    private int hash(String... strings) {
        return Arrays.hashCode(strings);
    }

}
