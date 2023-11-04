package com.omgservers.service.module.gateway.impl.operation.processMessage;

import io.smallrye.mutiny.Uni;

public interface ProcessMessageOperation {
    Uni<Void> processMessage(Long connectionId, String messageString);
}
