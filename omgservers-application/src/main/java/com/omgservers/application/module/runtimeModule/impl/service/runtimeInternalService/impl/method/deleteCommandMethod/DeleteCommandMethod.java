package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.deleteCommandMethod;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DeleteCommandInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.DeleteCommandInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteCommandMethod {
    Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandInternalRequest request);
}
