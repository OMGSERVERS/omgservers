package com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.handler;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.request.FireEventInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerHelpService.request.GetOrCreatePlayerHelpRequest;
import com.omgservers.application.module.userModule.impl.service.playerHelpService.response.GetOrCreatePlayerHelpResponse;
import com.omgservers.application.module.userModule.model.client.ClientModel;
import com.omgservers.application.module.userModule.model.player.PlayerModel;
import com.omgservers.application.module.userModule.model.user.UserModel;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import com.omgservers.application.module.internalModule.model.event.body.SignInRequestedEventBodyModel;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.tenantModule.impl.service.stageHelpService.request.ValidateStageSecretHelpRequest;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.module.internalModule.model.event.body.PlayerSignedInEventBodyModel;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.CreateClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.userInternalService.request.ValidateCredentialsInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.CreateClientInternalResponse;
import com.omgservers.application.module.userModule.impl.service.userInternalService.response.ValidateCredentialsInternalResponse;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.net.URI;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SignInRequestedEventHandlerImpl implements EventHandler {

    final UserModule userModule;
    final TenantModule tenantModule;
    final InternalModule internalModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SIGN_IN_REQUESTED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (SignInRequestedEventBodyModel) event.getBody();
        final var server = body.getServer();
        final var connection = body.getConnection();
        final var tenant = body.getTenant();
        final var stage = body.getStage();
        final var secret = body.getSecret();
        final var userUuid = body.getUser();
        final var password = body.getPassword();

        return validateStageSecret(tenant, stage, secret)
                .flatMap(voidItem -> validateCredentials(tenant, userUuid, password))
                .flatMap(user -> getOrCreatePlayer(userUuid, stage)
                        .flatMap(player -> {
                            final var playerUuid = player.getUuid();
                            return createClient(userUuid, playerUuid, server, connection)
                                    .flatMap(client -> {
                                        final var clientUuid = client.getUuid();
                                        return fireEvent(tenant, stage, userUuid, playerUuid, clientUuid);
                                    });
                        }))
                .replaceWith(true);
    }

    Uni<Void> validateStageSecret(final UUID tenant, final UUID stage, final String secret) {
        final var validateStageSecretHelpRequest = new ValidateStageSecretHelpRequest(tenant, stage, secret);
        return tenantModule.getStageHelpService().validateStageSecret(validateStageSecretHelpRequest)
                .replaceWithVoid();
    }

    Uni<UserModel> validateCredentials(final UUID tenant, final UUID user, final String password) {
        final var validateCredentialsServiceRequest = new ValidateCredentialsInternalRequest(tenant, user, password);
        return userModule.getUserInternalService().validateCredentials(validateCredentialsServiceRequest)
                .map(ValidateCredentialsInternalResponse::getUser);
    }

    Uni<PlayerModel> getOrCreatePlayer(UUID user, UUID stage) {
        final var createPlayerHelpRequest = new GetOrCreatePlayerHelpRequest(user, stage);
        return userModule.getPlayerHelpService().getOrCreatePlayer(createPlayerHelpRequest)
                .map(GetOrCreatePlayerHelpResponse::getPlayer);
    }

    Uni<ClientModel> createClient(final UUID user,
                                  final UUID player,
                                  final URI server,
                                  final UUID connection) {
        final var request = new CreateClientInternalRequest(user, player, server, connection);
        return userModule.getClientInternalService().createClient(request)
                .map(CreateClientInternalResponse::getClient);
    }

    Uni<Void> fireEvent(final UUID tenant,
                        final UUID stage,
                        final UUID user,
                        final UUID player,
                        final UUID client) {
        final var event = PlayerSignedInEventBodyModel.createEvent(tenant, stage, user, player, client);
        final var request = new FireEventInternalRequest(event);
        return internalModule.getEventInternalService().fireEvent(request)
                .replaceWithVoid();
    }
}
