package com.omgservers.service.operation.getIdByStage;

import io.smallrye.mutiny.Uni;

public interface GetIdByStageOperation {
    Uni<Long> execute(final Long projectId, final String stage);
}
