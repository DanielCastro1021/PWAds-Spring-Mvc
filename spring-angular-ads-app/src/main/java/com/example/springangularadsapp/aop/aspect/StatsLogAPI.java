package com.example.springangularadsapp.aop.aspect;

import com.example.springangularadsapp.models.StatsLog;
import com.example.springangularadsapp.repository.StatsLogRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

@Component
@Aspect
public class StatsLogAPI {

    private final StatsLogRepository repository;

    public StatsLogAPI(StatsLogRepository repository) {
        this.repository = repository;
    }

    @Pointcut("execution(* com.example.springangularadsapp.controller..*.*(..))")
    void allMethodsInControllers() {
    }

    @Around(value = "allMethodsInControllers()")
    public Object profileExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        String requestId = UUID.randomUUID().toString();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String apiName = className + "." + methodName;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        StatsLog statsLog = new StatsLog(requestId, request.getHeader("host"), request.getMethod(), request.getRequestURI(), apiName, Arrays.toString(joinPoint.getArgs()), elapsedTime);
        this.repository.save(statsLog);
        return result;
    }
}
