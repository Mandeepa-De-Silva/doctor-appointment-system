package com.mandeepa.das_backend.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("within(com.mandeepa.das_backend.rest.controller..*)")
    public Object logApi(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = null;
        try {
            result = pjp.proceed();
            return result;
        } finally {
            long dur = System.currentTimeMillis() - start;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = (auth != null) ? auth.getName() : "anonymousUser";

            // Safely extract email for login request without a password
            if (pjp.getSignature().toShortString().contains("LoginController.signIn")) {
                for (Object arg : pjp.getArgs()) {
                    if (arg instanceof com.mandeepa.das_backend.server.user.UserSignInRequest req) {
                        user = req.getUsername(); // only log username
                        break;
                    }
                }
            }

            log.info("api={} user={} durationMs={}", pjp.getSignature().toShortString(), user, dur);
        }
    }
}
