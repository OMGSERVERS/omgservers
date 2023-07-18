package com.omgservers.application.module.gatewayModule.impl.operation.processMessageOperation;

import io.smallrye.mutiny.Uni;

import java.util.UUID;

public interface ProcessMessageOperation {
    Uni<Void> processMessage(UUID connection, String messageString);
}
