package com.omgservers.handler;

import com.omgservers.dto.internal.FireEventRequest;
import com.omgservers.dto.tenant.ValidateStageSecretRequest;
import com.omgservers.dto.user.FindPlayerRequest;
import com.omgservers.dto.user.FindPlayerResponse;
import com.omgservers.dto.user.SyncClientRequest;
import com.omgservers.dto.user.SyncPlayerRequest;
import com.omgservers.dto.user.ValidateCredentialsRequest;
import com.omgservers.dto.user.ValidateCredentialsResponse;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.PlayerSignedInEventBodyModel;
import com.omgservers.model.event.body.SignInRequestedEventBodyModel;
import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.factory.EventModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.user.UserModule;
import com.omgservers.module.user.factory.ClientModelFactory;
import com.omgservers.module.user.factory.PlayerModelFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SignInRequestedEventHandlerImpl implements EventHandler {

    final SystemModule systemModule;
    final TenantModule tenantModule;
    final UserModule userModule;

    final ClientModelFactory clientModelFactory;
    final PlayerModelFactory playerModelFactory;
    final EventModelFactory eventModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SIGN_IN_REQUESTED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (SignInRequestedEventBodyModel) event.getBody();
        final var server = body.getServer();
        final var connectionId = body.getConnectionId();
        final var tenantId = body.getTenantId();
        final var stageId = body.getStageId();
        final var secret = body.getSecret();
        final var userId = body.getUserId();
        final var password = body.getPassword();

        return validateStageSecret(tenantId, stageId, secret)
                .flatMap(voidItem -> validateCredentials(tenantId, userId, password))
                .flatMap(user -> getPlayer(userId, tenantId, stageId)
                        .flatMap(player -> {
                            final var playerId = player.getId();
                            return createClient(userId, playerId, server, connectionId)
                                    .flatMap(client -> {
                                        final var clientUuid = client.getId();
                                        return fireEvent(tenantId, stageId, userId, playerId, clientUuid);
                                    });
                        }))
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

    Uni<PlayerModel> getPlayer(Long userId, Long tenantId, Long stageId) {
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
        final var player = playerModelFactory.create(userId, tenantId, stageId, new PlayerConfigModel());
        final var syncPlayerRequest = new SyncPlayerRequest(player);
        return userModule.getPlayerService().syncPlayer(syncPlayerRequest)
                .replaceWith(player);
    }

    Uni<ClientModel> createClient(final Long userId,
                                  final Long playerId,
                                  final URI server,
                                  final Long connectionId) {
        final var client = clientModelFactory.create(userId, playerId, server, connectionId);
        final var request = new SyncClientRequest(client);
        return userModule.getClientService().syncClient(request)
                .replaceWith(client);
    }

    Uni<Void> fireEvent(final Long tenantId,
                        final Long stageId,
                        final Long userId,
                        final Long playerId,
                        final Long clientId) {
        final var eventBody = new PlayerSignedInEventBodyModel(tenantId, stageId, userId, playerId, clientId);
        final var event = eventModelFactory.create(eventBody);
        final var request = new FireEventRequest(event);
        return systemModule.getEventService().fireEvent(request)
                .replaceWithVoid();
    }
}
