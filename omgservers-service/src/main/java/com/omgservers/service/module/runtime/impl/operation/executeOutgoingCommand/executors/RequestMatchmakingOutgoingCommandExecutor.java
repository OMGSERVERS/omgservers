package com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.executors;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.schema.model.outgoingCommand.body.RequestMatchmakingOutgoingCommandBodyDto;
import com.omgservers.schema.model.request.MatchmakerRequestConfigDto;
import com.omgservers.schema.module.client.GetClientRequest;
import com.omgservers.schema.module.client.GetClientResponse;
import com.omgservers.schema.module.client.ViewClientMatchmakerRefsRequest;
import com.omgservers.schema.module.client.ViewClientMatchmakerRefsResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequestResponse;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.matchmaker.MatchmakerRequestModelFactory;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.OutgoingCommandExecutor;
import com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.HasRuntimeAssignmentOperation;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RequestMatchmakingOutgoingCommandExecutor implements OutgoingCommandExecutor {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final ClientModule clientModule;
    final UserModule userModule;

    final HasRuntimeAssignmentOperation hasRuntimeAssignmentOperation;
    final CheckShardOperation checkShardOperation;

    final MatchmakerRequestModelFactory matchmakerRequestModelFactory;
    final PgPool pgPool;

    @Override
    public OutgoingCommandQualifierEnum getQualifier() {
        return OutgoingCommandQualifierEnum.REQUEST_MATCHMAKING;
    }

    @Override
    public Uni<Void> execute(final Long runtimeId, final OutgoingCommandModel outgoingCommand) {
        log.debug("Execute request matchmaking outgoing command, outgoingCommand={}", outgoingCommand);

        final var commandBody = (RequestMatchmakingOutgoingCommandBodyDto) outgoingCommand.getBody();
        final var clientId = commandBody.getClientId();
        final var mode = commandBody.getMode();

        return checkShardOperation.checkShard(runtimeId.toString())
                .flatMap(shardModel -> pgPool
                        .withTransaction(sqlConnection -> hasRuntimeAssignmentOperation
                                .execute(
                                        sqlConnection,
                                        shardModel.shard(),
                                        runtimeId,
                                        clientId)
                                .flatMap(has -> {
                                    if (has) {
                                        return requestMatchmaking(clientId, mode);
                                    } else {
                                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_CLIENT_ID,
                                                String.format("wrong clientId, runtimeId=%s, clientId=%s",
                                                        runtimeId, clientId));
                                    }
                                })
                        )
                );
    }

    Uni<Void> requestMatchmaking(final Long clientId, final String mode) {
        return selectClientMatchmakerRef(clientId)
                .flatMap(clientMatchmakerRef -> {
                    final var matchmakerId = clientMatchmakerRef.getMatchmakerId();

                    return getClient(clientId)
                            .flatMap(client -> {
                                final var userId = client.getUserId();
                                final var playerId = client.getPlayerId();

                                return syncMatchmakerRequest(matchmakerId,
                                        userId,
                                        clientId,
                                        mode);
                            });
                })
                .replaceWithVoid();
    }

    Uni<ClientMatchmakerRefModel> selectClientMatchmakerRef(final Long clientId) {
        return viewClientMatchmakerRefs(clientId)
                .map(clientMatchmakerRefs -> {
                    if (clientMatchmakerRefs.isEmpty()) {
                        throw new ServerSideNotFoundException(ExceptionQualifierEnum.RUNTIME_NOT_FOUND,
                                String.format("matchmaker was not selected, clientId=%d", clientId));
                    } else {
                        return clientMatchmakerRefs.getLast();
                    }
                });
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientModule.getService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<List<ClientMatchmakerRefModel>> viewClientMatchmakerRefs(final Long clientId) {
        final var request = new ViewClientMatchmakerRefsRequest(clientId);
        return clientModule.getService().viewClientMatchmakerRefs(request)
                .map(ViewClientMatchmakerRefsResponse::getClientMatchmakerRefs);
    }

    Uni<Boolean> syncMatchmakerRequest(final Long matchmakerId,
                                       final Long userId,
                                       final Long clientId,
                                       final String mode) {
        final var requestConfig = new MatchmakerRequestConfigDto();
        final var requestModel = matchmakerRequestModelFactory.create(matchmakerId,
                userId,
                clientId,
                mode,
                requestConfig);
        final var request = new SyncMatchmakerRequestRequest(requestModel);
        return matchmakerModule.getService().syncMatchmakerRequestWithIdempotency(request)
                .map(SyncMatchmakerRequestResponse::getCreated);
    }
}
