package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.syncVersionMatchmakerRequest;

import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequestResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionMatchmakerRequestMethod {
    Uni<SyncVersionMatchmakerRequestResponse> syncVersionMatchmakerRequest(SyncVersionMatchmakerRequestRequest request);
}
