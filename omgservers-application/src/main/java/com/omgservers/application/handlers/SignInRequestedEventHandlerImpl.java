package com.omgservers.application.handlers;

import com.omgservers.application.factory.ClientModelFactory;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.module.userModule.impl.service.playerHelpService.request.GetOrCreatePlayerHelpRequest;
import com.omgservers.application.module.userModule.impl.service.playerHelpService.response.GetOrCreatePlayerHelpResponse;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.impl.factory.EventModelFactory;
import com.omgservers.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.dto.internalModule.FireEventShardRequest;
import com.omgservers.dto.userModule.SyncClientShardRequest;
import com.omgservers.dto.userModule.ValidateCredentialsInternalResponse;
import com.omgservers.dto.userModule.ValidateCredentialsShardRequest;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.PlayerSignedInEventBodyModel;
import com.omgservers.model.event.body.SignInRequestedEventBodyModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.dto.tenantModule.ValidateStageSecretRequest;
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

    final InternalModule internalModule;
    final TenantModule tenantModule;
    final UserModule userModule;

    final ClientModelFactory clientModelFactory;
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
                .flatMap(user -> getOrCreatePlayer(userId, stageId)
                        .flatMap(player -> {
                            final var playerUuid = player.getId();
                            return createClient(userId, playerUuid, server, connectionId)
                                    .flatMap(client -> {
                                        final var clientUuid = client.getId();
                                        return fireEvent(tenantId, stageId, userId, playerUuid, clientUuid);
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
        final var validateCredentialsServiceRequest = new ValidateCredentialsShardRequest(tenantId, userId, password);
        return userModule.getUserInternalService().validateCredentials(validateCredentialsServiceRequest)
                .map(ValidateCredentialsInternalResponse::getUser);
    }

    Uni<PlayerModel> getOrCreatePlayer(Long userId, Long stageId) {
        final var createPlayerHelpRequest = new GetOrCreatePlayerHelpRequest(userId, stageId);
        return userModule.getPlayerHelpService().getOrCreatePlayer(createPlayerHelpRequest)
                .map(GetOrCreatePlayerHelpResponse::getPlayer);
    }

    Uni<ClientModel> createClient(final Long userId,
                                  final Long playerId,
                                  final URI server,
                                  final Long connectionId) {
        final var client = clientModelFactory.create(playerId, server, connectionId);
        final var request = new SyncClientShardRequest(userId, client);
        return userModule.getClientInternalService().syncClient(request)
                .replaceWith(client);
    }

    Uni<Void> fireEvent(final Long tenantId,
                        final Long stageId,
                        final Long userId,
                        final Long playerId,
                        final Long clientId) {
        final var eventBody = new PlayerSignedInEventBodyModel(tenantId, stageId, userId, playerId, clientId);
        final var event = eventModelFactory.create(eventBody);
        final var request = new FireEventShardRequest(event);
        return internalModule.getEventShardedService().fireEvent(request)
                .replaceWithVoid();
    }
}
