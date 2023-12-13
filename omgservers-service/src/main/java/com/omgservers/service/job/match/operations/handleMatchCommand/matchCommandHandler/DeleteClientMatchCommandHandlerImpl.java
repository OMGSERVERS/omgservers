package com.omgservers.service.job.match.operations.handleMatchCommand.matchCommandHandler;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.gateway.RevokeRuntimeRequest;
import com.omgservers.model.dto.gateway.RevokeRuntimeResponse;
import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.model.matchCommand.MatchCommandQualifierEnum;
import com.omgservers.model.matchCommand.body.DeleteClientMatchCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.DeleteClientRuntimeCommandBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.RuntimeCommandModelFactory;
import com.omgservers.service.job.match.operations.handleMatchCommand.MatchCommandHandler;
import com.omgservers.service.module.gateway.GatewayModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteClientMatchCommandHandlerImpl implements MatchCommandHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final GatewayModule gatewayModule;
    final UserModule userModule;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Override
    public MatchCommandQualifierEnum getQualifier() {
        return MatchCommandQualifierEnum.DELETE_CLIENT;
    }

    @Override
    public Uni<Void> handle(MatchCommandModel matchCommand) {
        log.debug("Handle match command, {}", matchCommand);

        final var matchmakerId = matchCommand.getMatchmakerId();
        final var matchId = matchCommand.getMatchId();

        final var body = (DeleteClientMatchCommandBodyModel) matchCommand.getBody();
        final var userId = body.getUserId();
        final var clientId = body.getClientId();

        return matchmakerModule.getShortcutService().getMatch(matchmakerId, matchId)
                .flatMap(match -> {
                    final var runtimeId = match.getRuntimeId();
                    return runtimeModule.getShortcutService().findAndDeleteRuntimeClient(runtimeId, clientId)
                            .flatMap(wasRuntimeClientDeleted -> syncDeleteClientRuntimeCommand(runtimeId,
                                    userId,
                                    clientId)
                                    .flatMap(runtimeCommandWasCreated -> revokeIfClientExists(userId,
                                            clientId,
                                            runtimeId))
                                    .invoke(voidItem -> {
                                        log.info(
                                                "Client was deleted from match, " +
                                                        "clientId={}, " +
                                                        "matchmakerId={}, " +
                                                        "matchId={}, " +
                                                        "modeName={}," +
                                                        "matchCommandId={}",
                                                clientId,
                                                matchmakerId,
                                                matchId,
                                                match.getConfig().getModeConfig().getName(),
                                                matchCommand.getId());
                                    })
                            );
                })
                .replaceWithVoid();
    }


    Uni<Boolean> syncDeleteClientRuntimeCommand(final Long runtimeId,
                                                final Long userId,
                                                final Long clientId) {
        final var runtimeCommandBody = new DeleteClientRuntimeCommandBodyModel(userId, clientId);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody);
        return runtimeModule.getShortcutService().syncRuntimeCommand(runtimeCommand);
    }

    Uni<Void> revokeIfClientExists(final Long userId, final Long clientId, final Long runtimeId) {
        return userModule.getShortcutService().getClient(userId, clientId)
                .flatMap(client -> revokeRuntime(client, runtimeId))
                .onFailure(ServerSideNotFoundException.class).recoverWithNull()
                .replaceWithVoid();
    }

    Uni<Boolean> revokeRuntime(final ClientModel client, final Long runtimeId) {
        final var server = client.getServer();
        final var connectionId = client.getConnectionId();
        final var revokeRuntimeRequest = new RevokeRuntimeRequest(server, connectionId, runtimeId);
        return gatewayModule.getGatewayService().revokeRuntime(revokeRuntimeRequest)
                .map(RevokeRuntimeResponse::getRevoked);
    }
}
