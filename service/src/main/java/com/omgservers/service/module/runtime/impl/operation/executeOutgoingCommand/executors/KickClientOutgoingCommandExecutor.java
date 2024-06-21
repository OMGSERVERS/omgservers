package com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.executors;

import com.omgservers.model.dto.client.DeleteClientRequest;
import com.omgservers.model.dto.client.DeleteClientResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.matchmakerCommand.body.KickClientMatchmakerCommandBodyModel;
import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.body.KickClientOutgoingCommandBodyModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.matchmaker.MatchmakerCommandModelFactory;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand.OutgoingCommandExecutor;
import com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.hasRuntimeAssignment.HasRuntimeAssignmentOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final ClientModule clientModule;

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

        final var commandBody = (KickClientOutgoingCommandBodyModel) outgoingCommand.getBody();
        final var clientId = commandBody.getClientId();

        return checkShardOperation.checkShard(runtimeId.toString())
                .flatMap(shardModel -> pgPool
                        .withTransaction(sqlConnection -> hasRuntimeAssignmentOperation
                                .hasRuntimeAssignment(
                                        sqlConnection,
                                        shardModel.shard(),
                                        runtimeId,
                                        clientId)
                                .flatMap(has -> {
                                    if (has) {
                                        return kickClient(runtimeId, clientId);
                                    } else {
                                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.CLIENT_ID_WRONG,
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
                        log.info("Do kick client from the lobby, clientId={}, runtimeId={}",
                                clientId, runtimeId);

                        yield deleteClient(clientId)
                                .replaceWithVoid();
                    }
                    case MATCH -> {
                        log.info("Do kick client from the match, clientId={}, runtimeId={}",
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
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> deleteClient(final Long clientId) {
        final var request = new DeleteClientRequest(clientId);
        return clientModule.getClientService().deleteClient(request)
                .map(DeleteClientResponse::getDeleted);
    }

    Uni<Boolean> syncKickClientMatchmakerCommand(final Long matchmakerId,
                                                 final Long matchId,
                                                 final Long clientId) {
        final var commandBody = new KickClientMatchmakerCommandBodyModel(clientId, matchId);
        final var commandModel = matchmakerCommandModelFactory.create(matchmakerId, commandBody);
        final var request = new SyncMatchmakerCommandRequest(commandModel);
        return matchmakerModule.getMatchmakerService().syncMatchmakerCommand(request)
                .map(SyncMatchmakerCommandResponse::getCreated)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    log.info("Matchmaker was not found, KickClientMatchmakerCommand won't be created, " +
                            "matchmakerId={}, clientId={}", matchmakerId, clientId);
                    return Uni.createFrom().item(Boolean.FALSE);
                });
    }
}
