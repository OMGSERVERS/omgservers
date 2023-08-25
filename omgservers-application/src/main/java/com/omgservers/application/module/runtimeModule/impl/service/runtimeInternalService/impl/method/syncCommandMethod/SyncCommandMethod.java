package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.syncCommandMethod;

import com.omgservers.dto.runtimeModule.SyncCommandInternalRequest;
import com.omgservers.dto.runtimeModule.SyncCommandInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncCommandMethod {
    Uni<SyncCommandInternalResponse> syncCommand(SyncCommandInternalRequest request);
}
