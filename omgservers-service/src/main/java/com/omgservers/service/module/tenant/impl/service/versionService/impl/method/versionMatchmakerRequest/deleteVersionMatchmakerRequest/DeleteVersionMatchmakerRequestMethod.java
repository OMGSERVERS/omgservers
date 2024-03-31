package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRequest.deleteVersionMatchmakerRequest;

import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionMatchmakerRequestMethod {
    Uni<DeleteVersionMatchmakerRequestResponse> deleteVersionMatchmakerRequest(
            DeleteVersionMatchmakerRequestRequest request);
}
