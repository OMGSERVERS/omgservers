package com.omgservers.service.entrypoint.runtime.impl.service.runtimeService;

import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeResponse;
import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RuntimeService {

    Uni<CreateTokenRuntimeResponse> createToken(@Valid CreateTokenRuntimeRequest request);

    Uni<InterchangeRuntimeResponse> interchange(@Valid InterchangeRuntimeRequest request);
}
