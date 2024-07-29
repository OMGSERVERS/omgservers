package com.omgservers.service.entrypoint.router.impl.service.routerService;

import com.omgservers.schema.entrypoint.router.CreateTokenRouterRequest;
import com.omgservers.schema.entrypoint.router.CreateTokenRouterResponse;
import com.omgservers.schema.entrypoint.router.GetRuntimeServerUriRouterRequest;
import com.omgservers.schema.entrypoint.router.GetRuntimeServerUriRouterResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RouterService {

    Uni<CreateTokenRouterResponse> createToken(@Valid CreateTokenRouterRequest request);

    Uni<GetRuntimeServerUriRouterResponse> getRuntimeServerUri(@Valid GetRuntimeServerUriRouterRequest request);
}
