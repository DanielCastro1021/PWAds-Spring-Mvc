package com.example.springangularadsapp.aop.aspect;

import com.example.springangularadsapp.models.ERole;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Component
@Aspect
public class AccessValidation {

    private static final Logger logger = LoggerFactory.getLogger(AccessValidation.class);


    @Pointcut("@annotation(com.example.springangularadsapp.aop.annotation.AdminAccess)")
    public void methodsWithAdminAccessAnnotation() {
    }

    @Pointcut("@annotation(com.example.springangularadsapp.aop.annotation.ModeratorAccess)")
    public void methodsWithModeratorAccessAnnotation() {
    }

    @Pointcut("@annotation(com.example.springangularadsapp.aop.annotation.UserAccess)")
    public void methodsWithUserAccessAnnotation() {
    }

    @Pointcut("within(com.example.springangularadsapp.controller.TestController)")
    public void testControllerMethods() {
    }

    @Before(value = "methodsWithAdminAccessAnnotation() && testControllerMethods()")
    public void checkAdminPrivileges(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String header = request.getHeader("Bearer");
        logger.info("Header Token => " + header);
    }

    @Before(value = "methodsWithModeratorAccessAnnotation() && testControllerMethods()")
    public void checkModeratorPrivileges(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = servletRequestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String remoteAddr = request.getRemoteAddr();
        String jsonString = Arrays.toString(joinPoint.getArgs());
        String authTokenHeader = request.getHeader("Authorization");

        logger.info("--- request information --------");
        logger.info("remoteAddr: {} ", remoteAddr);
        logger.info("authTokenHeader : {}", authTokenHeader);
        logger.info("requestUI : {}", requestURI);
        logger.info("Controller : {}", joinPoint.getTarget().getClass());
        logger.info("method type: {}", method);
        logger.info("req paras: {}", jsonString);
        logger.info("--- request information -------- ---");

        /**
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         if (authentication.getAuthorities().contains(ERole.ROLE_MODERATOR)) {
         String currentPrincipalName = authentication.getName();
         logger.info("User Name ==>" + currentPrincipalName);
         authentication.getAuthorities().forEach(grantedAuthority -> logger.info("Authorization ==>" + grantedAuthority));
         }
         */

    }

    @Around(value = "methodsWithUserAccessAnnotation() && testControllerMethods()")
    public Object checkUserPrivileges(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return proceedingJoinPoint.proceed();
    }
}
