package com.omgservers.router.integration.impl.service;

import com.omgservers.model.dto.router.CreateTokenRouterRequest;
import com.omgservers.model.dto.router.CreateTokenRouterResponse;
import com.omgservers.model.dto.router.GetRuntimeServerUriRouterRequest;
import com.omgservers.model.dto.router.GetRuntimeServerUriRouterResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ServiceService {
    Uni<CreateTokenRouterResponse> createToken(@Valid CreateTokenRouterRequest request);

    Uni<GetRuntimeServerUriRouterResponse> getRuntimeServerUri(@Valid GetRuntimeServerUriRouterRequest request);
}
