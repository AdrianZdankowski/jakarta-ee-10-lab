package org.example.configuration.interceptor;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;
import lombok.extern.java.Log;
import org.example.airplane.entity.Airplane;
import org.example.configuration.interceptor.binding.LogOperation;

import java.util.UUID;
import java.util.logging.Level;

@Log
public class LogOperationInterceptor {

    @Inject
    private SecurityContext securityContext;

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {

         System.out.println("=== INTERCEPTOR ===");

        String operationName = context.getMethod().getAnnotation(LogOperation.class).value();
        String userName = securityContext != null && securityContext.getCallerPrincipal() != null 
                ? securityContext.getCallerPrincipal().getName() 
                : "anonymous";
        
        Object result = context.proceed();
        
        UUID resourceId = extractResourceId(context);
        
        String logMessage = String.format("User: %s, Operation: %s, Resource ID: %s", 
                userName, operationName, resourceId);
        
        System.out.println(logMessage);
        log.log(Level.INFO, logMessage);
        
        return result;
    }

    private UUID extractResourceId(InvocationContext context) {
        Object[] parameters = context.getParameters();
        if (parameters.length > 0) {
            Object firstParam = parameters[0];
            if (firstParam instanceof Airplane) {
                return ((Airplane) firstParam).getId();
            } else if (firstParam instanceof UUID) {
                return (UUID) firstParam;
            }
        }
        return null;
    }

}
