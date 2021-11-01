package com.lukascode.location.infrastructure.configuration.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EndpointLogger {

    private static final Logger LOG = LoggerFactory.getLogger(EndpointLogger.class);

    @Around("execution(* com.lukascode.location.api.LocalizationApi.*(..))")
    public Object forAllEndpoints(ProceedingJoinPoint pjp) throws Throwable {
        LOG.debug("Received request {}", pjp.getSignature().toShortString());
        long start = System.nanoTime();
        Object obj = pjp.proceed();
        long timeElapsedMs = (System.nanoTime() - start) / 1000000;
        LOG.debug("Request processed {request: {}, timeElapsed: {}ms}",
                pjp.getSignature().toShortString(), timeElapsedMs);
        return obj;
    }
}
