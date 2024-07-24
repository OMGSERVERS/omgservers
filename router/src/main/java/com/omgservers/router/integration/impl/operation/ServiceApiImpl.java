package com.omgservers.router.integration.impl.operation;

import com.omgservers.model.dto.router.CreateTokenRouterRequest;
import com.omgservers.model.dto.router.CreateTokenRouterResponse;
import com.omgservers.model.dto.router.GetRuntimeServerUriRouterRequest;
import com.omgservers.model.dto.router.GetRuntimeServerUriRouterResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ServiceApiImpl implements ServiceApi {

    @Override
    public Uni<CreateTokenRouterResponse> createToken(CreateTokenRouterRequest request) {
        return null;
    }

    @Override
    public Uni<GetRuntimeServerUriRouterResponse> getRuntimeServerUri(GetRuntimeServerUriRouterRequest request) {
        return null;
    }
}
