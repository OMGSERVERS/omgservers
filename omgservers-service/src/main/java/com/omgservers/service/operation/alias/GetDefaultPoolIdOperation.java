package com.omgservers.service.operation.alias;

import io.smallrye.mutiny.Uni;

public interface GetDefaultPoolIdOperation {
    Uni<Long> execute();
}
