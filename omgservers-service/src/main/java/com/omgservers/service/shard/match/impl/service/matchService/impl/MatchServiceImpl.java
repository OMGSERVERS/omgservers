package com.omgservers.service.shard.match.impl.service.matchService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.match.DeleteMatchRequest;
import com.omgservers.schema.module.match.DeleteMatchResponse;
import com.omgservers.schema.module.match.GetMatchRequest;
import com.omgservers.schema.module.match.GetMatchResponse;
import com.omgservers.schema.module.match.SyncMatchRequest;
import com.omgservers.schema.module.match.SyncMatchResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
import com.omgservers.service.shard.match.impl.operation.getMatchModuleClient.GetMatchModuleClientOperation;
import com.omgservers.service.shard.match.impl.service.matchService.MatchService;
import com.omgservers.service.shard.match.impl.service.matchService.impl.method.DeleteMatchMethod;
import com.omgservers.service.shard.match.impl.service.matchService.impl.method.GetMatchMethod;
import com.omgservers.service.shard.match.impl.service.matchService.impl.method.SyncMatchMethod;
import com.omgservers.service.shard.match.impl.service.webService.impl.api.MatchApi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchServiceImpl implements MatchService {

    final DeleteMatchMethod deleteMatchMethod;
    final SyncMatchMethod syncMatchMethod;
    final GetMatchMethod getMatchMethod;

    final GetMatchModuleClientOperation getMatchModuleClientOperation;
    final HandleShardedRequestOperation handleShardedRequestOperation;

    @Override
    public Uni<GetMatchResponse> execute(@Valid final GetMatchRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchModuleClientOperation::getClient,
                MatchApi::execute,
                getMatchMethod::execute);
    }

    @Override
    public Uni<SyncMatchResponse> execute(@Valid final SyncMatchRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchModuleClientOperation::getClient,
                MatchApi::execute,
                syncMatchMethod::execute);
    }

    @Override
    public Uni<SyncMatchResponse> executeWithIdempotency(@Valid final SyncMatchRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getMatch(), t.getMessage());
                            return Uni.createFrom().item(new SyncMatchResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteMatchResponse> execute(@Valid final DeleteMatchRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchModuleClientOperation::getClient,
                MatchApi::execute,
                deleteMatchMethod::execute);
    }
}
