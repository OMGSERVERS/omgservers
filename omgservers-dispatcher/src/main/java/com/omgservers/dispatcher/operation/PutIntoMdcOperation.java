package com.omgservers.dispatcher.operation;

public interface PutIntoMdcOperation {
    void putArbitrarySubject(String subject);

    void putInitializerSubject();

    void putAnonymousSubject();

    void putUnknownSubject();
}
