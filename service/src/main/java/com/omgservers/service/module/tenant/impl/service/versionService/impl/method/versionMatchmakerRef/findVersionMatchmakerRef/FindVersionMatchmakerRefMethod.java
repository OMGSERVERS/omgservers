package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRef.findVersionMatchmakerRef;

import com.omgservers.schema.module.tenant.FindVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.FindVersionMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindVersionMatchmakerRefMethod {
    Uni<FindVersionMatchmakerRefResponse> findVersionMatchmakerRef(FindVersionMatchmakerRefRequest request);
}
