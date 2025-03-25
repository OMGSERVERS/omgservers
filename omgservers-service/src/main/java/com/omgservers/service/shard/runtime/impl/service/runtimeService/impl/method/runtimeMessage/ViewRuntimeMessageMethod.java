package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeMessage;

import com.omgservers.schema.module.runtime.runtimeMessage.ViewRuntimeMessagesRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.ViewRuntimeMessagesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewRuntimeMessageMethod {
    Uni<ViewRuntimeMessagesResponse> execute(ViewRuntimeMessagesRequest request);
}
