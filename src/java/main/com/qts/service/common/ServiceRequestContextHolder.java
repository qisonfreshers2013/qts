package com.qts.service.common;

/**
 * Hold a service request context.
 */
public class ServiceRequestContextHolder {

    private static ThreadLocal<ServiceRequestContext> contextHolder = new ThreadLocal<ServiceRequestContext>();

    //~ Methods ========================================================================================================

    public static void clearContext() {
        contextHolder.set(null);
    }

    public static ServiceRequestContext getContext() {
        if (contextHolder.get() == null) {
            ServiceRequestContext ctx = new ServiceRequestContext();
            contextHolder.set(ctx);
        } 
        return contextHolder.get();
    }

    public static void setContext(ServiceRequestContext context) {
        contextHolder.set(context);
    }

}
