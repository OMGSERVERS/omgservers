package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.createRequestMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.matchmakerModule.impl.operation.insertRequestOperation.InsertRequestOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.MatchmakerInMemoryCache;
import com.omgservers.application.module.matchmakerModule.model.request.RequestModel;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.CreateRequestInternalResponse;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.CreateRequestInternalRequest;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateRequestMethodImpl implements CreateRequestMethod {

    final InternalModule internalModule;

    final InsertRequestOperation insertinsertRequestOperation;
    final CheckShardOperation checkShardOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;
    final PgPool pgPool;

    @Override
    public Uni<CreateRequestInternalResponse> createRequest(CreateRequestInternalRequest request) {
        CreateRequestInternalRequest.validate(request);

        return Uni.createFrom().voidItem()
                .flatMap(validatedProject -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shard -> {
                    final var requestModel = request.getRequest();
                    return createRequest(shard.shard(), requestModel)
                            .replaceWith(requestModel);
                })
                .map(CreateRequestInternalResponse::new);
    }

    Uni<Void> createRequest(final int shard, final RequestModel matchmakerRequest) {
        return pgPool.withTransaction(sqlConnection -> insertinsertRequestOperation
                        .insertRequest(sqlConnection, shard, matchmakerRequest))
                .invoke(voidItem -> matchmakerInMemoryCache.addRequest(matchmakerRequest));
    }
}
