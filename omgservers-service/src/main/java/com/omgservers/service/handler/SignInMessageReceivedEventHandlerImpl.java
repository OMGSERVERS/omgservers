package com.omgservers.service.handler;

import com.omgservers.model.assignedClient.AssignedClientModel;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.gateway.AssignClientRequest;
import com.omgservers.model.dto.gateway.AssignClientResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeGrantResponse;
import com.omgservers.model.dto.tenant.FindStageVersionIdRequest;
import com.omgservers.model.dto.tenant.FindStageVersionIdResponse;
import com.omgservers.model.dto.tenant.SelectVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SelectVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.SelectVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.SelectVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.ValidateStageSecretRequest;
import com.omgservers.model.dto.user.FindPlayerRequest;
import com.omgservers.model.dto.user.FindPlayerResponse;
import com.omgservers.model.dto.user.SyncClientRequest;
import com.omgservers.model.dto.user.SyncPlayerRequest;
import com.omgservers.model.dto.user.ValidateCredentialsRequest;
import com.omgservers.model.dto.user.ValidateCredentialsResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.SignInMessageReceivedEventBodyModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.runtimeCommand.body.SignInRuntimeCommandBodyModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.ClientModelFactory;
import com.omgservers.service.factory.PlayerModelFactory;
import com.omgservers.service.factory.RuntimeCommandModelFactory;
import com.omgservers.service.factory.RuntimeGrantModelFactory;
import com.omgservers.service.module.gateway.GatewayModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SignInMessageReceivedEventHandlerImpl implements EventHandler {

    final GatewayModule gatewayModule;
    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;
    final UserModule userModule;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;
    final RuntimeGrantModelFactory runtimeGrantModelFactory;
    final ClientModelFactory clientModelFactory;
    final PlayerModelFactory playerModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SIGN_IN_MESSAGE_RECEIVED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (SignInMessageReceivedEventBodyModel) event.getBody();
        final var server = body.getServer();
        final var connectionId = body.getConnectionId();
        final var tenantId = body.getTenantId();
        final var stageId = body.getStageId();
        final var secret = body.getSecret();
        final var userId = body.getUserId();
        final var password = body.getPassword();

        return validateStageSecret(tenantId, stageId, secret)
                .flatMap(voidItem -> validateCredentials(tenantId, userId, password))
                .flatMap(user -> findOrSyncPlayer(userId, tenantId, stageId)
                        .flatMap(player -> {
                            final var playerId = player.getId();
                            return findVersionId(tenantId, stageId)
                                    .flatMap(versionId -> selectVersionMatchmaker(tenantId, versionId)
                                            .flatMap(versionMatchmaker -> selectVersionRuntime(tenantId, versionId)
                                                    .flatMap(versionRuntime -> {
                                                                final var matchmakerId = versionMatchmaker
                                                                        .getMatchmakerId();
                                                                final var runtimeId = versionRuntime
                                                                        .getRuntimeId();
                                                                return createClient(userId,
                                                                        playerId,
                                                                        server,
                                                                        connectionId,
                                                                        versionId,
                                                                        matchmakerId,
                                                                        runtimeId)
                                                                        .call(client -> syncRuntimeGrantForClient(
                                                                                runtimeId,
                                                                                client))
                                                                        .call(client -> syncSignInRuntimeCommand(
                                                                                runtimeId,
                                                                                player,
                                                                                client))
                                                                        .call(client -> assignClient(player, client)
                                                                                .invoke(voidItem -> {
                                                                                    log.info("User signed in, " +
                                                                                                    "userId={}, " +
                                                                                                    "clientId={}, " +
                                                                                                    "tenantId={}, " +
                                                                                                    "stageId={}, " +
                                                                                                    "versionId={}",
                                                                                            userId,
                                                                                            client.getId(),
                                                                                            tenantId,
                                                                                            stageId,
                                                                                            client.getVersionId());
                                                                                })
                                                                        );
                                                            }
                                                    )
                                            )
                                    );
                        })
                )
                .replaceWith(true);
    }

    Uni<Void> validateStageSecret(final Long tenantId, final Long stageId, final String secret) {
        final var validateStageSecretHelpRequest = new ValidateStageSecretRequest(tenantId, stageId, secret);
        return tenantModule.getStageService().validateStageSecret(validateStageSecretHelpRequest)
                .replaceWithVoid();
    }

    Uni<UserModel> validateCredentials(final Long tenantId, final Long userId, final String password) {
        final var validateCredentialsServiceRequest = new ValidateCredentialsRequest(tenantId, userId, password);
        return userModule.getUserService().validateCredentials(validateCredentialsServiceRequest)
                .map(ValidateCredentialsResponse::getUser);
    }

    Uni<PlayerModel> findOrSyncPlayer(Long userId, Long tenantId, Long stageId) {
        return findPlayer(userId, stageId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> syncPlayer(userId, tenantId, stageId));
    }

    Uni<PlayerModel> findPlayer(Long userId, Long stageId) {
        final var findPlayerRequest = new FindPlayerRequest(userId, stageId);
        return userModule.getPlayerService().findPlayer(findPlayerRequest)
                .map(FindPlayerResponse::getPlayer);
    }

    Uni<PlayerModel> syncPlayer(Long userId, Long tenantId, Long stageId) {
        final var player = playerModelFactory.create(userId, tenantId, stageId);
        final var syncPlayerRequest = new SyncPlayerRequest(player);
        return userModule.getPlayerService().syncPlayer(syncPlayerRequest)
                .replaceWith(player);
    }

    Uni<Long> findVersionId(final Long tenantId, final Long stageId) {
        final var request = new FindStageVersionIdRequest(tenantId, stageId);
        return tenantModule.getVersionService().findStageVersionId(request)
                .map(FindStageVersionIdResponse::getVersionId);
    }

    Uni<VersionMatchmakerModel> selectVersionMatchmaker(final Long tenantId, final Long versionId) {
        final var request = new SelectVersionMatchmakerRequest(tenantId,
                versionId,
                SelectVersionMatchmakerRequest.Strategy.RANDOM);
        return tenantModule.getVersionService().selectVersionMatchmaker(request)
                .map(SelectVersionMatchmakerResponse::getVersionMatchmaker);
    }

    Uni<VersionRuntimeModel> selectVersionRuntime(final Long tenantId, final Long versionId) {
        final var request = new SelectVersionRuntimeRequest(tenantId,
                versionId,
                SelectVersionRuntimeRequest.Strategy.RANDOM);
        return tenantModule.getVersionService().selectVersionRuntime(request)
                .map(SelectVersionRuntimeResponse::getVersionRuntime);
    }

    Uni<ClientModel> createClient(final Long userId,
                                  final Long playerId,
                                  final URI server,
                                  final Long connectionId,
                                  final Long versionId,
                                  final Long defaultMatchmakerId,
                                  final Long defaultRuntimeId) {
        final var client = clientModelFactory.create(userId,
                playerId,
                server,
                connectionId,
                versionId,
                defaultMatchmakerId,
                defaultRuntimeId);
        final var request = new SyncClientRequest(client);
        return userModule.getClientService().syncClient(request)
                .replaceWith(client);
    }

    Uni<Boolean> syncRuntimeGrantForClient(Long runtimeId, ClientModel client) {
        final var runtimeGrant = runtimeGrantModelFactory.create(
                runtimeId,
                client.getUserId(),
                client.getId(),
                RuntimeGrantTypeEnum.USER_CLIENT);
        final var request = new SyncRuntimeGrantRequest(runtimeGrant);
        return runtimeModule.getRuntimeService().syncRuntimeGrant(request)
                .map(SyncRuntimeGrantResponse::getCreated);
    }

    Uni<Boolean> syncSignInRuntimeCommand(final Long runtimeId,
                                          final PlayerModel player,
                                          final ClientModel client) {
        final var userId = client.getUserId();
        final var clientId = client.getId();
        final var playerAttributes = player.getAttributes();
        final var playerObject = player.getObject();
        final var runtimeCommandBody = new SignInRuntimeCommandBodyModel(userId,
                clientId,
                playerAttributes,
                playerObject);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody);
        final var syncRuntimeCommandShardedRequest = new SyncRuntimeCommandRequest(runtimeCommand);
        return runtimeModule.getRuntimeService().syncRuntimeCommand(syncRuntimeCommandShardedRequest)
                .map(SyncRuntimeCommandResponse::getCreated);
    }

    Uni<Void> assignClient(final PlayerModel player,
                           final ClientModel client) {
        final var tenantId = player.getTenantId();
        final var stageId = player.getStageId();
        final var userId = player.getUserId();
        final var playerId = player.getId();
        final var server = client.getServer();
        final var connectionId = client.getConnectionId();
        final var assignedClient = new AssignedClientModel(tenantId, stageId, userId, playerId, client.getId());
        final var request = new AssignClientRequest(server, connectionId, assignedClient);
        return gatewayModule.getGatewayService().assignClient(request)
                .map(AssignClientResponse::getAssigned)
                .replaceWithVoid();
    }
}
