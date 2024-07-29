package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRequest.getVersionMatchmakerRequest;

import com.omgservers.schema.module.tenant.GetVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.GetVersionMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionMatchmakerRequestMethod {

    Uni<GetVersionMatchmakerRequestResponse> getVersionMatchmakerRequest(GetVersionMatchmakerRequestRequest request);
}
