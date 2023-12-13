package com.omgservers.service.handler;

import com.omgservers.model.assignedClient.AssignedClientModel;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.gateway.AssignClientRequest;
import com.omgservers.model.dto.gateway.AssignClientResponse;
import com.omgservers.model.dto.gateway.RespondMessageRequest;
import com.omgservers.model.dto.gateway.RespondMessageResponse;
import com.omgservers.model.dto.user.SyncClientRequest;
import com.omgservers.model.dto.user.SyncPlayerRequest;
import com.omgservers.model.dto.user.SyncUserRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.SignUpMessageReceivedEventBodyModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.CredentialsMessageBodyModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.runtimeCommand.body.SignUpRuntimeCommandBodyModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.factory.ClientModelFactory;
import com.omgservers.service.factory.MessageModelFactory;
import com.omgservers.service.factory.PlayerModelFactory;
import com.omgservers.service.factory.RuntimeClientModelFactory;
import com.omgservers.service.factory.RuntimeCommandModelFactory;
import com.omgservers.service.factory.UserModelFactory;
import com.omgservers.service.module.gateway.GatewayModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.security.SecureRandom;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SignUpMessageReceivedEventHandlerImpl implements EventHandler {

    final GatewayModule gatewayModule;
    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;
    final UserModule userModule;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;
    final RuntimeClientModelFactory runtimeClientModelFactory;
    final MessageModelFactory messageModelFactory;
    final ClientModelFactory clientModelFactory;
    final PlayerModelFactory playerModelFactory;
    final UserModelFactory userModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SIGN_UP_MESSAGE_RECEIVED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (SignUpMessageReceivedEventBodyModel) event.getBody();
        final var server = body.getServer();
        final var connectionId = body.getConnectionId();
        final var tenantId = body.getTenantId();
        final var stageId = body.getStageId();
        final var secret = body.getSecret();

        //TODO: improve it
        final var password = String.valueOf(new SecureRandom().nextLong());

        return tenantModule.getShortcutService().validateStageSecret(tenantId, stageId, secret)
                .flatMap(validateStageSecretResponse -> syncUser(password))
                .flatMap(user -> {
                    final var userId = user.getId();
                    return respondCredentials(server, connectionId, userId, password)
                            .flatMap(respondMessageResponse -> createPlayer(userId, tenantId, stageId))
                            .flatMap(player -> {
                                final var playerId = player.getId();
                                return tenantModule.getShortcutService().findStageVersionId(tenantId, stageId)
                                        .flatMap(versionId -> tenantModule.getShortcutService()
                                                .selectVersionMatchmaker(tenantId, versionId)
                                                .flatMap(versionMatchmaker -> tenantModule.getShortcutService()
                                                        .selectVersionRuntime(tenantId, versionId)
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
                                                                            .call(client -> syncRuntimeClient(
                                                                                    runtimeId,
                                                                                    client))
                                                                            .call(client -> syncSignUpRuntimeCommand(
                                                                                    runtimeId,
                                                                                    client))
                                                                            .call(client -> assignClient(player, client)
                                                                                    .invoke(voidItem -> {
                                                                                        log.info("User signed up, " +
                                                                                                        "client={}/{}, " +
                                                                                                        "stage={}/{}, " +
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
                            });

                })
                .replaceWith(true);
    }

    Uni<UserModel> syncUser(final String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, passwordHash);
        final var request = new SyncUserRequest(user);
        return userModule.getUserService().syncUser(request)
                .replaceWith(user);
    }

    Uni<RespondMessageResponse> respondCredentials(URI server, Long connectionId, Long userId, String password) {
        final var body = new CredentialsMessageBodyModel(userId, password);
        final var message = messageModelFactory.create(MessageQualifierEnum.CREDENTIALS_MESSAGE, body);
        final var request = new RespondMessageRequest(server, connectionId, message);
        return gatewayModule.getGatewayService().respondMessage(request);
    }

    Uni<PlayerModel> createPlayer(Long userId, Long tenantId, Long stageId) {
        final var player = playerModelFactory.create(userId, tenantId, stageId);
        final var syncPlayerRequest = new SyncPlayerRequest(player);
        return userModule.getPlayerService().syncPlayer(syncPlayerRequest)
                .replaceWith(player);
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

    Uni<Boolean> syncRuntimeClient(Long runtimeId, ClientModel client) {
        final var runtimeClient = runtimeClientModelFactory.create(
                runtimeId,
                client.getUserId(),
                client.getId());
        return runtimeModule.getShortcutService().syncRuntimeClient(runtimeClient);
    }

    Uni<Boolean> syncSignUpRuntimeCommand(final Long runtimeId,
                                          final ClientModel client) {
        final var clientId = client.getId();
        final var userId = client.getUserId();
        final var runtimeCommandBody = new SignUpRuntimeCommandBodyModel(userId, clientId);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody);
        return runtimeModule.getShortcutService().syncRuntimeCommand(runtimeCommand);
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
