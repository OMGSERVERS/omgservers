package com.omgservers.service.operation.getIdByProject;

import io.smallrye.mutiny.Uni;

public interface GetIdByProjectOperation {
    Uni<Long> execute(final Long tenantId, final String project);
}
