package com.example.springangularadsapp.aop.aspect;

import com.example.springangularadsapp.security.exception.AccessValidationException;
import com.example.springangularadsapp.constants.enumeration.ERole;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


@Component
@Aspect
public class AccessValidation {

    private static final Logger logger = LoggerFactory.getLogger(AccessValidation.class);


    @Pointcut("@annotation(com.example.springangularadsapp.constants.annotation.AdminAccess)")
    public void methodsWithAdminAccessAnnotation() {
    }

    @Pointcut("@annotation(com.example.springangularadsapp.constants.annotation.ModeratorAccess)")
    public void methodsWithModeratorAccessAnnotation() {
    }

    @Pointcut("@annotation(com.example.springangularadsapp.constants.annotation.UserAccess)")
    public void methodsWithUserAccessAnnotation() {
    }

    @Pointcut("within(com.example.springangularadsapp.controller.TestController)")
    public void testControllerMethods() {
    }

    @Around(value = "methodsWithAdminAccessAnnotation() && testControllerMethods()")
    public Object checkAdminPrivileges(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        if (!request.isUserInRole("ROLE_ADMIN")) throw new AccessValidationException(ERole.ROLE_ADMIN);
        else return proceedingJoinPoint.proceed();
    }

    @Around(value = "methodsWithModeratorAccessAnnotation() && testControllerMethods()")
    public Object checkModeratorPrivileges(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        if (!request.isUserInRole("ROLE_MODERATOR")) throw new AccessValidationException(ERole.ROLE_ADMIN);
        else return proceedingJoinPoint.proceed();

    }

    @Around(value = "methodsWithUserAccessAnnotation() && testControllerMethods()")
    public Object checkUserPrivileges(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        if (request.isUserInRole("ROLE_USER") || request.isUserInRole("ROLE_MODERATOR") || request.isUserInRole("ROLE_ADMIN"))
            return proceedingJoinPoint.proceed();
        else throw new AccessValidationException(ERole.ROLE_USER);
    }
}
