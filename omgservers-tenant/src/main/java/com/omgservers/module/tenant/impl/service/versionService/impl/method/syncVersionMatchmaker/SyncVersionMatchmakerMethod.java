package com.omgservers.module.tenant.impl.service.versionService.impl.method.syncVersionMatchmaker;

import com.omgservers.dto.tenant.SyncVersionMatchmakerRequest;
import com.omgservers.dto.tenant.SyncVersionMatchmakerResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionMatchmakerMethod {
    Uni<SyncVersionMatchmakerResponse> syncVersionMatchmaker(SyncVersionMatchmakerRequest request);
}
