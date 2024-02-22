package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.findVersionMatchmakerRequest;

import com.omgservers.model.dto.tenant.FindVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface FindVersionMatchmakerRequestMethod {
    Uni<FindVersionMatchmakerRequestResponse> findVersionMatchmakerRequest(FindVersionMatchmakerRequestRequest request);
}
