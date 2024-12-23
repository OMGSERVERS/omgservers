package com.omgservers.service.entrypoint.runtime.impl.service.webService;

import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeResponse;
import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenRuntimeResponse> execute(CreateTokenRuntimeRequest request);

    Uni<InterchangeRuntimeResponse> execute(InterchangeRuntimeRequest request);
}
