package com.omgservers.service.operation.alias;

import io.smallrye.mutiny.Uni;

public interface GetIdByProjectOperation {
    Uni<Long> execute(final Long tenantId, final String project);
}
