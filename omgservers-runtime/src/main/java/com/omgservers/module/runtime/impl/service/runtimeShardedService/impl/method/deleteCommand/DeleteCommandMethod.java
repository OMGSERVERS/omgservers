package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.deleteCommand;

import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteCommandMethod {
    Uni<DeleteRuntimeCommandShardedResponse> deleteCommand(DeleteRuntimeCommandShardedRequest request);
}
