package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.syncActorMethod;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.SyncActorInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.SyncActorInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncActorMethod {
    Uni<SyncActorInternalResponse> syncActor(SyncActorInternalRequest request);
}
