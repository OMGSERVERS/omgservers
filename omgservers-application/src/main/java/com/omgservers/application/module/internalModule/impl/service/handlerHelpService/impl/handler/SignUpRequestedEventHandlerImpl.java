package com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.handler;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.request.FireEventInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerHelpService.request.GetOrCreatePlayerHelpRequest;
import com.omgservers.application.module.userModule.impl.service.playerHelpService.response.GetOrCreatePlayerHelpResponse;
import com.omgservers.application.module.userModule.impl.service.userHelpService.request.RespondClientHelpRequest;
import com.omgservers.application.module.userModule.model.client.ClientModel;
import com.omgservers.application.module.userModule.model.player.PlayerModel;
import com.omgservers.application.module.userModule.model.user.UserModel;
import com.omgservers.application.module.userModule.model.user.UserRoleEnum;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import com.omgservers.application.module.internalModule.model.event.body.SignUpRequestedEventBodyModel;
import com.omgservers.application.module.internalModule.model.event.body.PlayerSignedUpEventBodyModel;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.CreateClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.userInternalService.request.CreateUserInternalRequest;
import com.omgservers.application.module.gatewayModule.model.message.MessageModel;
import com.omgservers.application.module.gatewayModule.model.message.MessageQualifierEnum;
import com.omgservers.application.module.gatewayModule.model.message.body.CredentialsMessageBodyModel;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.tenantModule.impl.service.stageHelpService.request.ValidateStageSecretHelpRequest;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.CreateClientInternalResponse;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.net.URI;
import java.security.SecureRandom;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SignUpRequestedEventHandlerImpl implements EventHandler {

    final UserModule userModule;
    final TenantModule tenantModule;
    final InternalModule internalModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SIGN_UP_REQUESTED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (SignUpRequestedEventBodyModel) event.getBody();
        final var server = body.getServer();
        final var connection = body.getConnection();
        final var tenant = body.getTenant();
        final var stage = body.getStage();
        final var secret = body.getSecret();

        //TODO: improve it
        final var password = String.valueOf(Math.abs(new SecureRandom().nextLong()));

        return validateStageSecret(tenant, stage, secret)
                .flatMap(voidItem -> createUser(password))
                .flatMap(user -> {
                    final var userUuid = user.getUuid();
                    return createPlayer(userUuid, stage)
                            .flatMap(player -> {
                                final var playerUuid = player.getUuid();
                                return createClient(userUuid, playerUuid, server, connection)
                                        .call(client -> {
                                            final var clientUuid = client.getUuid();
                                            return respondCredentials(userUuid, clientUuid, password)
                                                    .flatMap(voidItem -> fireEvent(tenant, stage, userUuid, playerUuid, clientUuid));
                                        });
                            });
                })
                .replaceWith(true);
    }

    Uni<Void> validateStageSecret(final UUID tenant, final UUID stage, final String stageSecret) {
        final var request = new ValidateStageSecretHelpRequest(tenant, stage, stageSecret);
        return tenantModule.getStageHelpService().validateStageSecret(request)
                .replaceWithVoid();
    }

    Uni<UserModel> createUser(final String password) {
        final var uuid = UUID.randomUUID();
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = UserModel.create(uuid, UserRoleEnum.PLAYER, passwordHash);
        final var createUserServiceRequest = new CreateUserInternalRequest(user);
        return userModule.getUserInternalService().createUser(createUserServiceRequest)
                .replaceWith(user);
    }

    Uni<PlayerModel> createPlayer(UUID user, UUID stage) {
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

    Uni<Void> respondCredentials(UUID user, UUID client, String password) {
        final var body = new CredentialsMessageBodyModel(user, password);
        final var message = MessageModel.create(MessageQualifierEnum.CREDENTIALS_MESSAGE, body);
        final var respondClientHelpRequest = new RespondClientHelpRequest(user, client, message);
        return userModule.getUserHelpService().respondClient(respondClientHelpRequest)
                .replaceWithVoid();
    }

    Uni<Void> fireEvent(final UUID tenant,
                        final UUID stage,
                        final UUID user,
                        final UUID player,
                        final UUID client) {
        final var event = PlayerSignedUpEventBodyModel.createEvent(tenant, stage, user, player, client);
        final var request = new FireEventInternalRequest(event);
        return internalModule.getEventInternalService().fireEvent(request)
                .replaceWithVoid();
    }
}
