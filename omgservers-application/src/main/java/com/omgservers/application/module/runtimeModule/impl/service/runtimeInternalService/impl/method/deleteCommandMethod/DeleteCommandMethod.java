package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.deleteCommandMethod;

import com.omgservers.dto.runtimeModule.DeleteCommandRoutedRequest;
import com.omgservers.dto.runtimeModule.DeleteCommandInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteCommandMethod {
    Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandRoutedRequest request);
}
