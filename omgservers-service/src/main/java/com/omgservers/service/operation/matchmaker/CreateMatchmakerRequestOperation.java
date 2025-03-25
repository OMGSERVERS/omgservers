package com.omgservers.service.operation.matchmaker;

import io.smallrye.mutiny.Uni;

public interface CreateMatchmakerRequestOperation {
    Uni<Boolean> execute(Long deploymentId,
                         Long clientId,
                         String mode);
}
