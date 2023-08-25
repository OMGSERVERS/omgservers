package com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.syncPlayerMethod;

import com.omgservers.dto.userModule.SyncPlayerInternalRequest;
import com.omgservers.dto.userModule.SyncPlayerInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPlayerMethod {
    Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerInternalRequest request);
}
