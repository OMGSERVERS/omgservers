package com.omgservers.application.module.gatewayModule.impl.operation.processMessageOperation;

import java.util.UUID;

public interface ProcessMessageOperation {
    void processMessage(UUID connection, String messageString);
}
