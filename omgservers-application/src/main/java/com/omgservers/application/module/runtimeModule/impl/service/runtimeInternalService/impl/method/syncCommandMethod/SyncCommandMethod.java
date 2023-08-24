package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.syncCommandMethod;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.SyncCommandInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.SyncCommandInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncCommandMethod {
    Uni<SyncCommandInternalResponse> syncCommand(SyncCommandInternalRequest request);
}
