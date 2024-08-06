package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRequest.findVersionMatchmakerRequest;

import com.omgservers.schema.module.tenant.FindVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.FindVersionMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface FindVersionMatchmakerRequestMethod {
    Uni<FindVersionMatchmakerRequestResponse> findVersionMatchmakerRequest(FindVersionMatchmakerRequestRequest request);
}
