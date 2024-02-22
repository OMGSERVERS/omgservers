package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersionMatchmakerRef;

import com.omgservers.model.dto.tenant.GetVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionMatchmakerRefMethod {

    Uni<GetVersionMatchmakerRefResponse> getVersionMatchmakerRef(GetVersionMatchmakerRefRequest request);
}
