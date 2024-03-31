package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRequest.getVersionMatchmakerRequest;

import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface GetVersionMatchmakerRequestMethod {

    Uni<GetVersionMatchmakerRequestResponse> getVersionMatchmakerRequest(GetVersionMatchmakerRequestRequest request);
}
