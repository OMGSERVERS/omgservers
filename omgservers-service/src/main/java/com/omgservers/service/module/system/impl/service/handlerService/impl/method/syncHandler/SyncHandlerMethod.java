package com.omgservers.service.module.system.impl.service.handlerService.impl.method.syncHandler;

import com.omgservers.model.dto.system.SyncHandlerRequest;
import com.omgservers.model.dto.system.SyncHandlerResponse;
import io.smallrye.mutiny.Uni;

public interface SyncHandlerMethod {
    Uni<SyncHandlerResponse> syncHandler(SyncHandlerRequest request);
}
