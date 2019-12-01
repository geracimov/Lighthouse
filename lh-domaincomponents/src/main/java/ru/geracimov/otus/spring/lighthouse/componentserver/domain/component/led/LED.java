package ru.geracimov.otus.spring.lighthouse.componentserver.domain.component.led;

import ru.geracimov.otus.spring.lighthouse.componentserver.domain.Component;

public interface LED extends Component {

    void on();

    void off();

    boolean isOn();

}
