package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.deleteCommandMethod;

import com.omgservers.dto.runtimeModule.DeleteCommandShardRequest;
import com.omgservers.dto.runtimeModule.DeleteCommandInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteCommandMethod {
    Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandShardRequest request);
}
