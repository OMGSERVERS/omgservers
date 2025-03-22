package com.omgservers.service.operation.alias;

import io.smallrye.mutiny.Uni;

public interface GetIdByVersionOperation {
    Uni<Long> execute(final Long tenantId, final String version);
}
