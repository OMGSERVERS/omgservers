package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.syncVersionMatchmaker;

import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerResponse;
import io.smallrye.mutiny.Uni;

public interface SyncVersionMatchmakerMethod {
    Uni<SyncVersionMatchmakerResponse> syncVersionMatchmaker(SyncVersionMatchmakerRequest request);
}
