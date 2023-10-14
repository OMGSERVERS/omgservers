package com.omgservers.handler;

import com.omgservers.dto.gateway.AssignClientRequest;
import com.omgservers.dto.gateway.AssignClientResponse;
import com.omgservers.dto.script.CallScriptRequest;
import com.omgservers.dto.script.CallScriptResponse;
import com.omgservers.dto.script.SyncScriptRequest;
import com.omgservers.dto.tenant.GetStageVersionIdRequest;
import com.omgservers.dto.tenant.GetStageVersionIdResponse;
import com.omgservers.dto.tenant.ValidateStageSecretRequest;
import com.omgservers.dto.user.FindPlayerRequest;
import com.omgservers.dto.user.FindPlayerResponse;
import com.omgservers.dto.user.SyncClientRequest;
import com.omgservers.dto.user.SyncPlayerRequest;
import com.omgservers.dto.user.ValidateCredentialsRequest;
import com.omgservers.dto.user.ValidateCredentialsResponse;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.assignedClient.AssignedClientModel;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.SignInRequestedEventBodyModel;
import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.script.ScriptConfigModel;
import com.omgservers.model.script.ScriptModel;
import com.omgservers.model.script.ScriptTypeEnum;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.body.SignedInScriptEventBodyModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.module.gateway.GatewayModule;
import com.omgservers.module.script.ScriptModule;
import com.omgservers.module.script.factory.ScriptModelFactory;
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
import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SignInRequestedEventHandlerImpl implements EventHandler {

    final GatewayModule gatewayModule;
    final SystemModule systemModule;
    final TenantModule tenantModule;
    final ScriptModule scriptModule;
    final UserModule userModule;

    final ClientModelFactory clientModelFactory;
    final PlayerModelFactory playerModelFactory;
    final ScriptModelFactory scriptModelFactory;
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
                            return syncClient(userId, playerId, server, connectionId)
                                    .flatMap(client -> getVersionId(tenantId, stageId)
                                            .flatMap(versionId -> syncScript(tenantId, versionId, client))
                                            .flatMap(script -> {
                                                final var scriptId = client.getScriptId();
                                                final var clientId = client.getId();
                                                return callScript(scriptId, userId, playerId, clientId)
                                                        .replaceWithVoid();
                                            })
                                            .flatMap(script -> assignClient(player, client))
                                            .invoke(voidItem -> {
                                                log.info("User signed in, " +
                                                                "userId={}, " +
                                                                "clientId={}, " +
                                                                "tenantId={}, " +
                                                                "stageId={}, " +
                                                                "scriptId={}",
                                                        userId,
                                                        client.getId(),
                                                        tenantId,
                                                        stageId,
                                                        client.getScriptId());
                                            })
                                    );
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

    Uni<Boolean> callScript(final Long scriptId,
                            final Long userId,
                            final Long playerId,
                            final Long clientId) {
        final var scriptEventBody = SignedInScriptEventBodyModel.builder()
                .userId(userId)
                .playerId(playerId)
                .clientId(clientId)
                .build();

        final var request = new CallScriptRequest(scriptId,
                Collections.singletonList(new ScriptEventModel(scriptEventBody.getQualifier(), scriptEventBody)));
        return scriptModule.getScriptService().callScript(request)
                .map(CallScriptResponse::getResult);
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
