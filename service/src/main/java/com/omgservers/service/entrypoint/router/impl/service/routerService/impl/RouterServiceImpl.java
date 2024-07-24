package com.omgservers.service.entrypoint.router.impl.service.routerService.impl;

import com.omgservers.model.dto.router.CreateTokenRouterRequest;
import com.omgservers.model.dto.router.CreateTokenRouterResponse;
import com.omgservers.model.dto.router.GetRuntimeServerUriRouterRequest;
import com.omgservers.model.dto.router.GetRuntimeServerUriRouterResponse;
import com.omgservers.service.entrypoint.router.impl.service.routerService.RouterService;
import com.omgservers.service.entrypoint.router.impl.service.routerService.impl.method.createToken.CreateTokenMethod;
import com.omgservers.service.entrypoint.router.impl.service.routerService.impl.method.getRuntimeServerUri.GetRuntimeServerUriMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RouterServiceImpl implements RouterService {

    final GetRuntimeServerUriMethod getRuntimeServerUriMethod;
    final CreateTokenMethod createTokenMethod;

    @Override
    public Uni<CreateTokenRouterResponse> createToken(@Valid CreateTokenRouterRequest request) {
        return createTokenMethod.createToken(request);
    }

    @Override
    public Uni<GetRuntimeServerUriRouterResponse> getRuntimeServerUri(@Valid GetRuntimeServerUriRouterRequest request) {
        return getRuntimeServerUriMethod.getRuntimeServerUri(request);
    }
}
