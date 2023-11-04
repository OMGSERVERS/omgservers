package com.omgservers.worker.component;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class TokenContainer extends AtomicReference<String> {
}
