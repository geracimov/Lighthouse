package ru.geracimov.otus.spring.lighthouse.componentserver.service;

import com.pi4j.io.gpio.*;
import com.pi4j.platform.Platform;

import java.util.HashMap;
import java.util.Map;


public class PinProviderMapper {
    private static final Map<Platform, Class<? extends PinProvider>> map;

    static {
        map = new HashMap<>();
        map.put(Platform.BANANAPI, BananaPiPin.class);
        map.put(Platform.BANANAPRO, BananaProPin.class);
        map.put(Platform.BPI, BpiPin.class);
        map.put(Platform.NANOPI, NanoPiPin.class);
        map.put(Platform.ODROID, OdroidC1Pin.class);
        map.put(Platform.ORANGEPI, OrangePiPin.class);
        map.put(Platform.RASPBERRYPI, RaspiPin.class);
    }

    public static Class<? extends PinProvider> get(Platform platform) {
        final PinProvider pinProvider = new PinProvider(){

        };
        return map.getOrDefault(platform, RaspiPin.class);
    }

}
