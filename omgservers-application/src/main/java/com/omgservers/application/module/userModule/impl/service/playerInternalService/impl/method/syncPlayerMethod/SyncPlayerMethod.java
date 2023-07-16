package com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.syncPlayerMethod;

import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.SyncPlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.response.SyncPlayerInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPlayerMethod {
    Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerInternalRequest request);
}
