package com.omgservers.service.module.runtime.impl.service.doService.impl.method.doKickClient;

import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.dto.runtime.DoKickClientRequest;
import com.omgservers.model.dto.runtime.DoKickClientResponse;
import com.omgservers.model.matchmakerCommand.body.DeleteClientMatchmakerCommandBodyModel;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.factory.MatchmakerCommandModelFactory;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.runtime.impl.operation.hasRuntimeClient.HasRuntimeClientOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoKickClientMethodImpl implements DoKickClientMethod {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;

    final HasRuntimeClientOperation hasRuntimeClientOperation;
    final CheckShardOperation checkShardOperation;

    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DoKickClientResponse> doKickClient(final DoKickClientRequest request) {
        log.debug("Do kick client, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var runtimeId = request.getRuntimeId();
                    final var clientId = request.getClientId();
                    return pgPool.withTransaction(sqlConnection -> hasRuntimeClientOperation.hasRuntimeClient(
                                    sqlConnection,
                                    shardModel.shard(),
                                    runtimeId,
                                    clientId)
                            .flatMap(has -> {
                                if (has) {
                                    return doKickClient(runtimeId, clientId);
                                } else {
                                    throw new ServerSideForbiddenException(
                                            String.format("runtime client was not found, " +
                                                            "runtimeId=%s, clientId=%s",
                                                    runtimeId, clientId));
                                }
                            })
                    );
                })
                .replaceWith(new DoKickClientResponse());
    }

    Uni<Boolean> doKickClient(final Long runtimeId,
                              final Long clientId) {
        log.info("Do kick client, clientId={}, runtimeId={}",
                clientId, runtimeId);

        return runtimeModule.getShortcutService().getRuntime(runtimeId)
                .flatMap(runtime -> {
                    if (Objects.isNull(runtime.getConfig().getMatchConfig())) {
                        throw new ServerSideConflictException("Runtime is corrupted, matchConfig is null, " +
                                "runtimeId=" + runtimeId);
                    }

                    final var matchmakerId = runtime.getConfig().getMatchConfig().getMatchmakerId();
                    return syncDeleteClientMatchmakerCommand(matchmakerId, clientId)
                            .replaceWithVoid();
                })
                .replaceWith(true);
    }

    Uni<Boolean> syncDeleteClientMatchmakerCommand(final Long matchmakerId,
                                                   final Long clientId) {
        final var commandBody = new DeleteClientMatchmakerCommandBodyModel(clientId);
        final var commandModel = matchmakerCommandModelFactory.create(matchmakerId, commandBody);
        final var request = new SyncMatchmakerCommandRequest(commandModel);
        return matchmakerModule.getMatchmakerService().syncMatchmakerCommand(request)
                .map(SyncMatchmakerCommandResponse::getCreated);
    }
}
