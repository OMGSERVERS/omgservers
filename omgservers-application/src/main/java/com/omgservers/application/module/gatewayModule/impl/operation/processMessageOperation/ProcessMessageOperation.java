package com.omgservers.application.module.gatewayModule.impl.operation.processMessageOperation;

import io.smallrye.mutiny.Uni;

public interface ProcessMessageOperation {
    Uni<Void> processMessage(Long connectionId, String messageString);
}
