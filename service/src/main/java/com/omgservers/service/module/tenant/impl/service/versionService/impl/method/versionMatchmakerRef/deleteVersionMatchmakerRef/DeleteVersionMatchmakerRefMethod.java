package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRef.deleteVersionMatchmakerRef;

import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionMatchmakerRefMethod {
    Uni<DeleteVersionMatchmakerRefResponse> deleteVersionMatchmakerRef(DeleteVersionMatchmakerRefRequest request);
}
