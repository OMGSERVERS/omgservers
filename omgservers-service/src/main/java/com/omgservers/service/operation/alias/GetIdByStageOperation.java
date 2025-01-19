package com.omgservers.service.operation.alias;

import io.smallrye.mutiny.Uni;

public interface GetIdByStageOperation {
    Uni<Long> execute(Long tenantId, Long projectId, String stage);
}
