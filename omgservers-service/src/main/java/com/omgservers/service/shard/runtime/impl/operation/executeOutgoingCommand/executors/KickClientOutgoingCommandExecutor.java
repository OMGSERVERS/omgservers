package com.omgservers.service.shard.runtime.impl.operation.executeOutgoingCommand.executors;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.matchmakerCommand.body.KickClientMatchmakerCommandBodyDto;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.schema.model.outgoingCommand.body.KickClientOutgoingCommandBodyDto;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.module.client.DeleteClientRequest;
import com.omgservers.schema.module.client.DeleteClientResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.matchmaker.MatchmakerCommandModelFactory;
import com.omgservers.service.shard.client.ClientShard;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import com.omgservers.service.shard.runtime.RuntimeShard;
import com.omgservers.service.shard.runtime.impl.operation.executeOutgoingCommand.OutgoingCommandExecutor;
import com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment.HasRuntimeAssignmentOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class KickClientOutgoingCommandExecutor implements OutgoingCommandExecutor {

    final MatchmakerShard matchmakerShard;
    final RuntimeShard runtimeShard;
    final ClientShard clientShard;

    final HasRuntimeAssignmentOperation hasRuntimeAssignmentOperation;
    final CheckShardOperation checkShardOperation;

    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;
    final PgPool pgPool;

    @Override
    public OutgoingCommandQualifierEnum getQualifier() {
        return OutgoingCommandQualifierEnum.KICK_CLIENT;
    }

    @Override
    public Uni<Void> execute(final Long runtimeId, final OutgoingCommandModel outgoingCommand) {
        log.debug("Execute kick client outgoing command, outgoingCommand={}", outgoingCommand);

        final var commandBody = (KickClientOutgoingCommandBodyDto) outgoingCommand.getBody();
        final var clientId = commandBody.getClientId();

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
                                        return kickClient(runtimeId, clientId);
                                    } else {
                                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_CLIENT_ID,
                                                String.format("wrong clientId, runtimeId=%s, clientId=%s",
                                                        runtimeId, clientId));
                                    }
                                })
                        )
                );
    }

    Uni<Void> kickClient(final Long runtimeId,
                         final Long clientId) {
        return getRuntime(runtimeId)
                .flatMap(runtime -> switch (runtime.getQualifier()) {
                    case LOBBY -> {
                        log.debug("Do kick client from the lobby, clientId={}, runtimeId={}",
                                clientId, runtimeId);

                        yield deleteClient(clientId)
                                .replaceWithVoid();
                    }
                    case MATCH -> {
                        log.debug("Do kick client from the match, clientId={}, runtimeId={}",
                                clientId, runtimeId);

                        final var matchmakerId = runtime.getConfig().getMatchConfig().getMatchmakerId();
                        final var matchId = runtime.getConfig().getMatchConfig().getMatchId();

                        yield syncKickClientMatchmakerCommand(matchmakerId, matchId, clientId)
                                .replaceWithVoid();
                    }
                });
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeShard.getService().execute(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> deleteClient(final Long clientId) {
        final var request = new DeleteClientRequest(clientId);
        return clientShard.getService().deleteClient(request)
                .map(DeleteClientResponse::getDeleted);
    }

    Uni<Boolean> syncKickClientMatchmakerCommand(final Long matchmakerId,
                                                 final Long matchId,
                                                 final Long clientId) {
        final var commandBody = new KickClientMatchmakerCommandBodyDto(clientId, matchId);
        final var commandModel = matchmakerCommandModelFactory.create(matchmakerId, commandBody);
        final var request = new SyncMatchmakerCommandRequest(commandModel);
        return matchmakerShard.getService().execute(request)
                .map(SyncMatchmakerCommandResponse::getCreated)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    log.debug("Matchmaker was not found, matchmakerId={}, clientId={}", matchmakerId, clientId);
                    return Uni.createFrom().item(Boolean.FALSE);
                });
    }
}
