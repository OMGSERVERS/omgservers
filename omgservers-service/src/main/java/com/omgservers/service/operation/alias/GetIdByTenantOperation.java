package com.omgservers.service.operation.alias;

import io.smallrye.mutiny.Uni;

public interface GetIdByTenantOperation {
    Uni<Long> execute(String tenant);
}
