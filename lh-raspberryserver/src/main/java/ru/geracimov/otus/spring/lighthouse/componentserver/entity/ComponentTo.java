package ru.geracimov.otus.spring.lighthouse.componentserver.entity;

import com.pi4j.io.gpio.PinMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "COMPONENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentTo {

    @Id
    @Column(name = "PIN_NUMBER")
    Integer address;

    @Column(name = "NAME")
    String name;

    @Column(name = "CLASSNAME")
    String className;

    @Column(name = "PIN_MODE")
    @Enumerated(EnumType.STRING)
    PinMode pinMode;

}
