package com.omgservers.service.entrypoint.runtime.impl.service.webService;

import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeResponse;
import com.omgservers.schema.entrypoint.runtime.GetConfigRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.GetConfigRuntimeResponse;
import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenRuntimeResponse> createToken(CreateTokenRuntimeRequest request);

    Uni<GetConfigRuntimeResponse> getConfig(GetConfigRuntimeRequest request);

    Uni<InterchangeRuntimeResponse> interchange(InterchangeRuntimeRequest request);
}
