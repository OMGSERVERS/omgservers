package com.omgservers.connector.operation;

public interface PutIntoMdcOperation {
    void putArbitrarySubject(String subject);

    void putInitializerSubject();

    void putAnonymousSubject();

    void putUnknownSubject();
}
