package com.omgservers.service.operation.getIdByTenant;

import io.smallrye.mutiny.Uni;

public interface GetIdByTenantOperation {
    Uni<Long> execute(final String tenant);
}
