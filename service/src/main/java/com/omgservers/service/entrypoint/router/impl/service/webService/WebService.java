package com.omgservers.service.entrypoint.router.impl.service.webService;

import com.omgservers.schema.entrypoint.router.CreateTokenRouterRequest;
import com.omgservers.schema.entrypoint.router.CreateTokenRouterResponse;
import com.omgservers.schema.entrypoint.router.GetRuntimeServerUriRouterRequest;
import com.omgservers.schema.entrypoint.router.GetRuntimeServerUriRouterResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenRouterResponse> createToken(CreateTokenRouterRequest request);

    Uni<GetRuntimeServerUriRouterResponse> getRuntimeServerUri(GetRuntimeServerUriRouterRequest request);
}
