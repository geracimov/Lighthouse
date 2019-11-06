package ru.geracimov.otus.spring.lighthouse.componentserver.helper;

import java.util.concurrent.TimeUnit;

public class JavaHelper {

    public static String currentMethodName() {
        return Thread.currentThread()
                     .getStackTrace()[2].getMethodName();
    }

    public static void sleep(long count){
        try {
            TimeUnit.SECONDS.sleep(count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
