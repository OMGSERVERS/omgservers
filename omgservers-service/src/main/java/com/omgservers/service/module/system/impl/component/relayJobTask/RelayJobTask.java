package com.omgservers.service.module.system.impl.component.relayJobTask;

import io.smallrye.mutiny.Uni;

public interface RelayJobTask {
    Uni<Void> executeTask();
}
