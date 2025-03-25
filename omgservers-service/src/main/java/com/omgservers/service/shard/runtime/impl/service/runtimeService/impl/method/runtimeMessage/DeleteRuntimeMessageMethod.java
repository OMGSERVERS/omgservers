package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeMessage;

import com.omgservers.schema.module.runtime.runtimeMessage.DeleteRuntimeMessageRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.DeleteRuntimeMessageResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteRuntimeMessageMethod {
    Uni<DeleteRuntimeMessageResponse> execute(DeleteRuntimeMessageRequest request);
}
