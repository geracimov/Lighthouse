package ru.geracimov.otus.spring.lighthouse.componentserver.helper;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JavaHelperTest {

    @Test
    void currentMethodNameTest() {
        assertEquals("currentMethodNameTest", JavaHelper.currentMethodName());
    }

}