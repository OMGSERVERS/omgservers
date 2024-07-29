package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRef.deleteVersionMatchmakerRef;

import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionMatchmakerRefMethod {
    Uni<DeleteVersionMatchmakerRefResponse> deleteVersionMatchmakerRef(DeleteVersionMatchmakerRefRequest request);
}
