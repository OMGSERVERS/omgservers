package com.omgservers.handler;

import com.omgservers.dto.gateway.AssignPlayerRequest;
import com.omgservers.dto.gateway.RespondMessageRequest;
import com.omgservers.dto.gateway.RespondMessageResponse;
import com.omgservers.dto.internal.FireEventRequest;
import com.omgservers.dto.script.SyncScriptRequest;
import com.omgservers.dto.tenant.GetStageVersionIdRequest;
import com.omgservers.dto.tenant.GetStageVersionIdResponse;
import com.omgservers.dto.tenant.ValidateStageSecretRequest;
import com.omgservers.dto.tenant.ValidateStageSecretResponse;
import com.omgservers.dto.user.SyncClientRequest;
import com.omgservers.dto.user.SyncPlayerRequest;
import com.omgservers.dto.user.SyncUserRequest;
import com.omgservers.model.assignedPlayer.AssignedPlayerModel;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.PlayerSignedUpEventBodyModel;
import com.omgservers.model.event.body.SignUpRequestedEventBodyModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.CredentialsMessageBodyModel;
import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.script.ScriptConfigModel;
import com.omgservers.model.script.ScriptModel;
import com.omgservers.model.script.ScriptTypeEnum;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.module.gateway.GatewayModule;
import com.omgservers.module.gateway.factory.MessageModelFactory;
import com.omgservers.module.script.ScriptModule;
import com.omgservers.module.script.factory.ScriptModelFactory;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.factory.EventModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.user.UserModule;
import com.omgservers.module.user.factory.ClientModelFactory;
import com.omgservers.module.user.factory.PlayerModelFactory;
import com.omgservers.module.user.factory.UserModelFactory;
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
class SignUpRequestedEventHandlerImpl implements EventHandler {

    final GatewayModule gatewayModule;
    final SystemModule systemModule;
    final TenantModule tenantModule;
    final ScriptModule scriptModule;
    final UserModule userModule;

    final MessageModelFactory messageModelFactory;
    final ScriptModelFactory scriptModelFactory;
    final ClientModelFactory clientModelFactory;
    final PlayerModelFactory playerModelFactory;
    final EventModelFactory eventModelFactory;
    final UserModelFactory userModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SIGN_UP_REQUESTED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (SignUpRequestedEventBodyModel) event.getBody();
        final var server = body.getServer();
        final var connectionId = body.getConnectionId();
        final var tenantId = body.getTenantId();
        final var stageId = body.getStageId();
        final var secret = body.getSecret();

        //TODO: improve it
        final var password = String.valueOf(new SecureRandom().nextLong());

        return validateStageSecret(tenantId, stageId, secret)
                .flatMap(validateStageSecretResponse -> syncUser(password))
                .flatMap(user -> {
                    final var userId = user.getId();
                    return respondCredentials(server, connectionId, userId, password)
                            .flatMap(respondMessageResponse -> syncPlayer(userId, tenantId, stageId))
                            .flatMap(player -> {
                                final var playerId = player.getId();
                                return syncClient(userId, playerId, server, connectionId)
                                        .flatMap(client -> getVersionId(tenantId, stageId)
                                                .flatMap(versionId -> syncScript(tenantId, versionId, client))
                                                .flatMap(script -> assignPlayer(player, client))
                                                .flatMap(voidItem -> {
                                                    final var clientId = client.getId();
                                                    return fireEvent(tenantId, stageId, userId, playerId, clientId);
                                                }));
                            });

                })
                .replaceWith(true);
    }

    Uni<ValidateStageSecretResponse> validateStageSecret(final Long tenantId, final Long stageId,
                                                         final String stageSecret) {
        final var request = new ValidateStageSecretRequest(tenantId, stageId, stageSecret);
        return tenantModule.getStageService().validateStageSecret(request);
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

    Uni<PlayerModel> syncPlayer(Long userId, Long tenantId, Long stageId) {
        final var player = playerModelFactory.create(userId, tenantId, stageId, new PlayerConfigModel());
        final var syncPlayerRequest = new SyncPlayerRequest(player);
        return userModule.getPlayerService().syncPlayer(syncPlayerRequest)
                .replaceWith(player);
    }

    Uni<ClientModel> syncClient(final Long userId,
                                final Long playerId,
                                final URI server,
                                final Long connectionId) {
        final var client = clientModelFactory.create(userId, playerId, server, connectionId);
        final var request = new SyncClientRequest(client);
        return userModule.getClientService().syncClient(request)
                .replaceWith(client);
    }

    Uni<Long> getVersionId(final Long tenantId, final Long stageId) {
        final var getStageVersionIdRequest = new GetStageVersionIdRequest(tenantId, stageId);
        return tenantModule.getVersionService().getStageVersionId(getStageVersionIdRequest)
                .map(GetStageVersionIdResponse::getVersionId);
    }

    Uni<ScriptModel> syncScript(final Long tenantId, final Long versionId, final ClientModel client) {
        final var type = ScriptTypeEnum.CLIENT;
        final var config = ScriptConfigModel.builder()
                .userId(client.getUserId())
                .playerId(client.getPlayerId())
                .clientId(client.getId())
                .build();
        final var script = scriptModelFactory.create(client.getScriptId(), tenantId, versionId, type, config);
        final var request = new SyncScriptRequest(script);
        return scriptModule.getScriptService().syncScript(request)
                .replaceWith(script);
    }

    Uni<Void> assignPlayer(final PlayerModel player,
                           final ClientModel client) {
        final var tenantId = player.getTenantId();
        final var stageId = player.getStageId();
        final var userId = player.getUserId();
        final var playerId = player.getId();
        final var server = client.getServer();
        final var connectionId = client.getConnectionId();
        final var assignedPlayer = new AssignedPlayerModel(tenantId, stageId, userId, playerId, client.getId());
        final var request = new AssignPlayerRequest(server, connectionId, assignedPlayer);
        return gatewayModule.getGatewayService().assignPlayer(request);
    }

    Uni<Void> fireEvent(final Long tenantId,
                        final Long stageId,
                        final Long userId,
                        final Long playerId,
                        final Long clientId) {
        final var eventBody = new PlayerSignedUpEventBodyModel(tenantId, stageId, userId, playerId, clientId);
        final var event = eventModelFactory.create(eventBody);
        final var request = new FireEventRequest(event);
        return systemModule.getEventService().fireEvent(request)
                .replaceWithVoid();
    }
}
