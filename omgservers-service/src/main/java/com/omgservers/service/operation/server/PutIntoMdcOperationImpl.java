package com.omgservers.service.operation.server;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.jboss.logmanager.MDC;

@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PutIntoMdcOperationImpl implements PutIntoMdcOperation {

    static private final String SUBJECT = "subject";
    static private final String SLOT = "slot";

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

    @Override
    public void putSlot(final int slot) {
        MDC.put(SLOT, String.valueOf(slot));
    }
}
