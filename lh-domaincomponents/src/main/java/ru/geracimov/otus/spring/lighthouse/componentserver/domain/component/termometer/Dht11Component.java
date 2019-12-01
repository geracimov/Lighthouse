package ru.geracimov.otus.spring.lighthouse.componentserver.domain.component.termometer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.pi4j.io.gpio.impl.GpioPinImpl;
import com.pi4j.wiringpi.Gpio;
import ru.geracimov.otus.spring.lighthouse.componentserver.domain.ComponentBase;
import ru.geracimov.otus.spring.lighthouse.componentserver.domain.ComponentType;
import ru.geracimov.otus.spring.lighthouse.componentserver.domain.exception.InvalidComponentModeException;

import static com.pi4j.io.gpio.PinMode.DIGITAL_INPUT;

@JsonRootName(value = "temperature")
public final class Dht11Component extends ComponentBase implements Thermometer {
    private static final int DHT_MAXCOUNT = 32000;
    private static final int DHT_PULSES = 41;
    private static final int DHT_WAIT_INTERVAL = 2000;
    private double calculatedTemperature = -1001;

    public Dht11Component(GpioPinImpl pin) {
        super(pin, ComponentType.TEMP);
        if (!pin.isMode(DIGITAL_INPUT)) {
            throw new InvalidComponentModeException(pin.getMode().getName());
        }
//        if (Gpio.wiringPiSetup() == -1) {
//            throw new RuntimeException("DHT_ERROR_GPIO");
//        }
    }

    @Override
    @JsonIgnore
    public void calcTemperature() {
        int atempts = 0;
        while (true) {
            try {
                int[] data = getRawData();

                if (data[4] != ((data[0] + data[1] + data[2] + data[3]) & 0xFF)) {
                    throw new Exception("DHT_ERROR_CHECKSUM");
                }
                double temperature = data[2];
                if (data[3] > 0) {
                    if (data[3] <= 9) {
                        temperature += data[3] / 10.0;
                    } else {
                        temperature += data[3] / 100.0;
                    }
                }
                calculatedTemperature = temperature;
            } catch (Exception e) {
                atempts++;
                if (atempts <= 3) {
                    Gpio.delay(DHT_WAIT_INTERVAL);
                    continue;
                }
                throw new RuntimeException("Atempts " + atempts, e);
            }
        }
    }

    public double getTemperature() {
        return calculatedTemperature;
    }

    @Override
    public TemperatureScale getScale() {
        return TemperatureScale.CELSIUS;
    }

    private int[] getRawData() throws Exception {

        int[] pulseCounts = new int[DHT_PULSES * 2];

        Gpio.pinMode(pin.getPin().getAddress(), Gpio.OUTPUT);
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        Gpio.digitalWrite(pin.getPin().getAddress(), Gpio.HIGH);
        Gpio.delay(500);

        Gpio.digitalWrite(pin.getPin().getAddress(), Gpio.LOW);
        Gpio.delay(20);

        Gpio.pinMode(pin.getPin().getAddress(), Gpio.INPUT);

        long count = 0;
        while (Gpio.digitalRead(pin.getPin().getAddress()) == 1) {
            if (++count >= DHT_MAXCOUNT) {
                Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
                throw new Exception("DHT_ERROR_TIMEOUT");
            }
        }

        for (int i = 0; i < DHT_PULSES * 2; i += 2) {
            while (Gpio.digitalRead(pin.getPin().getAddress()) == 0) {
                if (++pulseCounts[i] >= DHT_MAXCOUNT) {
                    Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
                    throw new Exception("DHT_ERROR_TIMEOUT: " + pulseCounts[i] + " pulses, " + i);
                }
            }
            while (Gpio.digitalRead(pin.getPin().getAddress()) == 1) {
                if (++pulseCounts[i + 1] >= DHT_MAXCOUNT) {
                    Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
                    throw new Exception("DHT_ERROR_TIMEOUT: " + pulseCounts[i + 1] + " pulses, " + i);
                }
            }
        }

        Thread.currentThread().setPriority(Thread.NORM_PRIORITY);

        long threshold = 0;
        for (int i = 2; i < DHT_PULSES * 2; i += 2) {
            threshold += pulseCounts[i];
        }
        threshold /= DHT_PULSES - 1;

        int[] data = new int[5];
        for (int i = 3; i < DHT_PULSES * 2; i += 2) {
            int index = (i - 3) / 16;
            data[index] <<= 1;
            if (pulseCounts[i] >= threshold) {
                data[index] |= 1;
            }
        }
        return data;
    }

}
