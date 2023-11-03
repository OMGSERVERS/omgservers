package com.omgservers.worker.component;

import com.omgservers.worker.module.handler.HandlerModule;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class HandlerContainer extends AtomicReference<HandlerModule> {
}
