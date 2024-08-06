package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRequest.deleteVersionMatchmakerRequest;

import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionMatchmakerRequestMethod {
    Uni<DeleteVersionMatchmakerRequestResponse> deleteVersionMatchmakerRequest(
            DeleteVersionMatchmakerRequestRequest request);
}
