package com.omgservers.worker.component.tokenJobTask;

import io.smallrye.mutiny.Uni;

public interface TokenJobTask {
    Uni<Void> executeTask();
}
