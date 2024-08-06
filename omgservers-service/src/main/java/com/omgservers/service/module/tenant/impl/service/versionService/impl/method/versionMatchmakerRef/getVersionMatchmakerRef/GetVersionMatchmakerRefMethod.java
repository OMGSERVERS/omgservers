package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRef.getVersionMatchmakerRef;

import com.omgservers.schema.module.tenant.GetVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.GetVersionMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionMatchmakerRefMethod {

    Uni<GetVersionMatchmakerRefResponse> getVersionMatchmakerRef(GetVersionMatchmakerRefRequest request);
}
