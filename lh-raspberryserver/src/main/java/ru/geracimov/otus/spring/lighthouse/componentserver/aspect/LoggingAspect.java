package ru.geracimov.otus.spring.lighthouse.componentserver.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Pointcut("@within(ru.geracimov.otus.spring.lighthouse.componentserver.aspect.LoggingAspectAnnotation)")
    private void markedLoggingAspectAnnotation() {
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    private void markedRestControllerAnnotation() {
    }

    @Around("markedLoggingAspectAnnotation() || " +
            "markedRestControllerAnnotation()")
    public Object doAccessCheck(ProceedingJoinPoint jp) throws Throwable {
        log.debug(formatString("Start", jp, Arrays.toString(jp.getArgs())));
        long started = System.currentTimeMillis();
        Object ret = jp.proceed();
        long finished = System.currentTimeMillis();
        log.debug(formatString("Finish", jp, (finished - started) + " ms"));

        return ret;
    }

    private String formatString(String stage, JoinPoint jp, String detail) {
        return String.format(">>> %s method %s.%s [%s]",
                             stage,
                             jp.getSignature()
                               .getDeclaringTypeName(),
                             jp.getSignature()
                               .getName(),
                             detail);

    }

}
