package com.omgservers.service.entrypoint.runtime.impl.service.runtimeService;

import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeResponse;
import com.omgservers.schema.entrypoint.runtime.InterchangeMessagesRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.InterchangeMessagesRuntimeResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RuntimeService {

    Uni<CreateTokenRuntimeResponse> execute(@Valid CreateTokenRuntimeRequest request);

    Uni<InterchangeMessagesRuntimeResponse> execute(@Valid InterchangeMessagesRuntimeRequest request);
}
