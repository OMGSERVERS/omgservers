package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.doUpdateMethod;

import com.omgservers.dto.runtimeModule.DoUpdateRoutedRequest;
import io.smallrye.mutiny.Uni;

public interface DoUpdateMethod {
    Uni<Void> doUpdate(DoUpdateRoutedRequest request);
}
