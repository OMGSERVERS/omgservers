package com.omgservers.worker.component.handlerHolder;

import com.omgservers.worker.module.handler.HandlerModule;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class HandlerContainer {

    final AtomicReference<HandlerModule> atomicReference;

    HandlerContainer() {
        this.atomicReference = new AtomicReference<>();
    }

    public void set(final HandlerModule handlerModule) {
        atomicReference.set(handlerModule);
    }

    public HandlerModule get() {
        return atomicReference.get();
    }
}
