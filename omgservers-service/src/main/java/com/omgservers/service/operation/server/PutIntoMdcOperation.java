package com.omgservers.service.operation.server;

public interface PutIntoMdcOperation {
    void putArbitrarySubject(String subject);

    void putInitializerSubject();

    void putAnonymousSubject();

    void putUnknownSubject();

    void putSlot(int slot);
}
