package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.doUpdateMethod;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DoUpdateInternalRequest;
import io.smallrye.mutiny.Uni;

public interface DoUpdateMethod {
    Uni<Void> doUpdate(DoUpdateInternalRequest request);
}
