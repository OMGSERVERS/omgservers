package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRequest.syncVersionMatchmakerRequest;

import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionMatchmakerRequestMethod {
    Uni<SyncVersionMatchmakerRequestResponse> syncVersionMatchmakerRequest(SyncVersionMatchmakerRequestRequest request);
}
