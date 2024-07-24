package com.omgservers.service.entrypoint.router.impl.service.routerService;

import com.omgservers.model.dto.router.CreateTokenRouterRequest;
import com.omgservers.model.dto.router.CreateTokenRouterResponse;
import com.omgservers.model.dto.router.GetRuntimeServerUriRouterRequest;
import com.omgservers.model.dto.router.GetRuntimeServerUriRouterResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RouterService {

    Uni<CreateTokenRouterResponse> createToken(@Valid CreateTokenRouterRequest request);

    Uni<GetRuntimeServerUriRouterResponse> getRuntimeServerUri(@Valid GetRuntimeServerUriRouterRequest request);
}
