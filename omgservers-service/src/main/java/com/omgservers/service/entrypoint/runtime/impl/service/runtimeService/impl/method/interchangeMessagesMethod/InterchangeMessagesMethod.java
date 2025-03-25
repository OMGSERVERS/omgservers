package com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl.method.interchangeMessagesMethod;

import com.omgservers.schema.entrypoint.runtime.InterchangeMessagesRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.InterchangeMessagesRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface InterchangeMessagesMethod {
    Uni<InterchangeMessagesRuntimeResponse> execute(InterchangeMessagesRuntimeRequest request);
}
