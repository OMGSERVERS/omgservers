package com.omgservers.service.entrypoint.router.impl.service.webService.impl;

import com.omgservers.model.dto.router.CreateTokenRouterRequest;
import com.omgservers.model.dto.router.CreateTokenRouterResponse;
import com.omgservers.model.dto.router.GetRuntimeServerUriRouterRequest;
import com.omgservers.model.dto.router.GetRuntimeServerUriRouterResponse;
import com.omgservers.service.entrypoint.router.impl.service.routerService.RouterService;
import com.omgservers.service.entrypoint.router.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final RouterService routerService;

    @Override
    public Uni<CreateTokenRouterResponse> createToken(final CreateTokenRouterRequest request) {
        return routerService.createToken(request);
    }

    @Override
    public Uni<GetRuntimeServerUriRouterResponse> getRuntimeServerUri(final GetRuntimeServerUriRouterRequest request) {
        return routerService.getRuntimeServerUri(request);
    }
}
