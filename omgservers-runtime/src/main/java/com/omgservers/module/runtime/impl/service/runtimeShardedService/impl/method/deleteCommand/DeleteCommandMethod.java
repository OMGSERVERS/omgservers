package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.deleteCommand;

import com.omgservers.dto.runtime.DeleteCommandShardedRequest;
import com.omgservers.dto.runtime.DeleteCommandInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteCommandMethod {
    Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandShardedRequest request);
}
