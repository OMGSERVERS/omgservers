package com.omgservers.service.entrypoint.router.impl.service.routerService.impl.method.getRuntimeServerUri;

import com.omgservers.schema.entrypoint.router.GetRuntimeServerUriRouterRequest;
import com.omgservers.schema.entrypoint.router.GetRuntimeServerUriRouterResponse;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetRuntimeServerUriMethodImpl implements GetRuntimeServerUriMethod {

    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<GetRuntimeServerUriRouterResponse> getRuntimeServerUri(final GetRuntimeServerUriRouterRequest request) {
        log.debug("Get runtime server uri, request={}", request);
        return checkShardOperation.checkShard(request.getRuntimeId().toString())
                .map(shardModel -> new GetRuntimeServerUriRouterResponse(shardModel.serverUri()));
    }
}
