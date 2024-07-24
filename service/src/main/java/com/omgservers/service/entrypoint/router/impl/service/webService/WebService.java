package com.omgservers.service.entrypoint.router.impl.service.webService;

import com.omgservers.model.dto.router.CreateTokenRouterRequest;
import com.omgservers.model.dto.router.CreateTokenRouterResponse;
import com.omgservers.model.dto.router.GetRuntimeServerUriRouterRequest;
import com.omgservers.model.dto.router.GetRuntimeServerUriRouterResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenRouterResponse> createToken(CreateTokenRouterRequest request);

    Uni<GetRuntimeServerUriRouterResponse> getRuntimeServerUri(GetRuntimeServerUriRouterRequest request);
}
