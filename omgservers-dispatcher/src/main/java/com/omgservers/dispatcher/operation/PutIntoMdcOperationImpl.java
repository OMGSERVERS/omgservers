package com.omgservers.dispatcher.operation;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.jboss.logmanager.MDC;

@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PutIntoMdcOperationImpl implements PutIntoMdcOperation {

    static private final String SUBJECT = "subject";

    @Override
    public void putArbitrarySubject(final String subject) {
        MDC.put(SUBJECT, subject);
    }

    @Override
    public void putInitializerSubject() {
        MDC.put(SUBJECT, "initializer");
    }

    @Override
    public void putAnonymousSubject() {
        MDC.put(SUBJECT, "anonymous");
    }

    @Override
    public void putUnknownSubject() {
        MDC.put(SUBJECT, "unknown");
    }
}
