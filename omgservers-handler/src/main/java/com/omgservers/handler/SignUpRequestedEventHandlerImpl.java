package com.omgservers.handler;

import com.omgservers.dto.internal.FireEventRequest;
import com.omgservers.dto.tenant.ValidateStageSecretRequest;
import com.omgservers.dto.user.RespondClientRequest;
import com.omgservers.dto.user.SyncClientRequest;
import com.omgservers.dto.user.SyncPlayerRequest;
import com.omgservers.dto.user.SyncUserRequest;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.PlayerSignedUpEventBodyModel;
import com.omgservers.model.event.body.SignUpRequestedEventBodyModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.CredentialsMessageBodyModel;
import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.module.gateway.factory.MessageModelFactory;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.factory.EventModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.user.UserModule;
import com.omgservers.module.user.factory.ClientModelFactory;
import com.omgservers.module.user.factory.PlayerModelFactory;
import com.omgservers.module.user.factory.UserModelFactory;
import com.omgservers.operation.generateId.GenerateIdOperation;
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

    final SystemModule systemModule;
    final TenantModule tenantModule;
    final UserModule userModule;

    final GenerateIdOperation generateIdOperation;

    final MessageModelFactory messageModelFactory;
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
                .flatMap(voidItem -> syncUser(password))
                .flatMap(user -> {
                    final var userid = user.getId();
                    return syncPlayer(userid, tenantId, stageId)
                            .flatMap(player -> {
                                final var playerId = player.getId();
                                return syncClient(userid, playerId, server, connectionId)
                                        .call(client -> {
                                            final var clientId = client.getId();
                                            return respondCredentials(userid, clientId, password)
                                                    .flatMap(voidItem -> fireEvent(tenantId, stageId, userid, playerId, clientId));
                                        });
                            });
                })
                .replaceWith(true);
    }

    Uni<Void> validateStageSecret(final Long tenantId, final Long stageId, final String stageSecret) {
        final var request = new ValidateStageSecretRequest(tenantId, stageId, stageSecret);
        return tenantModule.getStageService().validateStageSecret(request)
                .replaceWithVoid();
    }

    Uni<UserModel> syncUser(final String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, passwordHash);
        final var syncUserInternalRequest = new SyncUserRequest(user);
        return userModule.getUserService().syncUser(syncUserInternalRequest)
                .replaceWith(user);
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

    Uni<Void> respondCredentials(Long userId, Long clientId, String password) {
        final var body = new CredentialsMessageBodyModel(userId, password);
        final var message = messageModelFactory.create(MessageQualifierEnum.CREDENTIALS_MESSAGE, body);
        final var respondClientHelpRequest = new RespondClientRequest(userId, clientId, message);
        return userModule.getUserService().respondClient(respondClientHelpRequest)
                .replaceWithVoid();
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
