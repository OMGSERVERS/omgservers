package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.findVersionMatchmakerRef;

import com.omgservers.model.dto.tenant.FindVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindVersionMatchmakerRefMethod {
    Uni<FindVersionMatchmakerRefResponse> findVersionMatchmakerRef(FindVersionMatchmakerRefRequest request);
}
