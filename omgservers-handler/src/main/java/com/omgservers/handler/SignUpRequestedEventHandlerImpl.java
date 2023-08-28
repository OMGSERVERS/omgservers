package com.omgservers.handler;

import com.omgservers.dto.internal.FireEventShardedRequest;
import com.omgservers.dto.tenant.ValidateStageSecretRequest;
import com.omgservers.dto.user.SyncClientShardedRequest;
import com.omgservers.dto.user.SyncUserShardedRequest;
import com.omgservers.factory.MessageModelFactory;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.PlayerSignedUpEventBodyModel;
import com.omgservers.model.event.body.SignUpRequestedEventBodyModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.CredentialsMessageBodyModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.factory.EventModelFactory;
import com.omgservers.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.user.UserModule;
import com.omgservers.module.user.factory.ClientModelFactory;
import com.omgservers.module.user.factory.UserModelFactory;
import com.omgservers.module.user.impl.service.playerService.request.GetOrCreatePlayerHelpRequest;
import com.omgservers.module.user.impl.service.playerService.response.GetOrCreatePlayerHelpResponse;
import com.omgservers.module.user.impl.service.userService.request.RespondClientRequest;
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

    final InternalModule internalModule;
    final TenantModule tenantModule;
    final UserModule userModule;

    final GenerateIdOperation generateIdOperation;

    final MessageModelFactory messageModelFactory;
    final ClientModelFactory clientModelFactory;
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
        final var password = String.valueOf(Math.abs(new SecureRandom().nextLong()));

        return validateStageSecret(tenantId, stageId, secret)
                .flatMap(voidItem -> createUser(password))
                .flatMap(user -> {
                    final var userUuid = user.getId();
                    return createPlayer(userUuid, stageId)
                            .flatMap(player -> {
                                final var playerUuid = player.getId();
                                return createClient(userUuid, playerUuid, server, connectionId)
                                        .call(client -> {
                                            final var clientId = client.getId();
                                            return respondCredentials(userUuid, clientId, password)
                                                    .flatMap(voidItem -> fireEvent(tenantId, stageId, userUuid, playerUuid, clientId));
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

    Uni<UserModel> createUser(final String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, passwordHash);
        final var syncUserInternalRequest = new SyncUserShardedRequest(user);
        return userModule.getUserShardedService().syncUser(syncUserInternalRequest)
                .replaceWith(user);
    }

    Uni<PlayerModel> createPlayer(Long userId, Long stageId) {
        final var createPlayerHelpRequest = new GetOrCreatePlayerHelpRequest(userId, stageId);
        return userModule.getPlayerService().getOrCreatePlayer(createPlayerHelpRequest)
                .map(GetOrCreatePlayerHelpResponse::getPlayer);
    }

    Uni<ClientModel> createClient(final Long userId,
                                  final Long playerId,
                                  final URI server,
                                  final Long connectionId) {
        final var client = clientModelFactory.create(playerId, server, connectionId);
        final var request = new SyncClientShardedRequest(userId, client);
        return userModule.getClientShardedService().syncClient(request)
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
        final var request = new FireEventShardedRequest(event);
        return internalModule.getEventShardedService().fireEvent(request)
                .replaceWithVoid();
    }
}
