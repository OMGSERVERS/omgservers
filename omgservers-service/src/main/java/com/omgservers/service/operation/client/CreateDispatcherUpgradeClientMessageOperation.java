package com.omgservers.service.operation.client;

import io.smallrye.mutiny.Uni;

public interface CreateDispatcherUpgradeClientMessageOperation {
    Uni<Boolean> execute(String wsToken,
                         Long clientId);
}
