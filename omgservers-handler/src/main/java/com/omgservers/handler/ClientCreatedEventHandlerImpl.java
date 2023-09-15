package com.omgservers.handler;

import com.omgservers.dto.script.SyncScriptRequest;
import com.omgservers.dto.tenant.GetStageVersionIdRequest;
import com.omgservers.dto.tenant.GetStageVersionIdResponse;
import com.omgservers.dto.user.GetClientRequest;
import com.omgservers.dto.user.GetClientResponse;
import com.omgservers.dto.user.GetPlayerRequest;
import com.omgservers.dto.user.GetPlayerResponse;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ClientCreatedEventBodyModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.script.ScriptConfigModel;
import com.omgservers.model.script.ScriptModel;
import com.omgservers.model.script.ScriptTypeEnum;
import com.omgservers.module.script.ScriptModule;
import com.omgservers.module.script.factory.ScriptModelFactory;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.factory.JobModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClientCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final ScriptModule scriptModule;
    final SystemModule systemModule;
    final UserModule userModule;

    final ScriptModelFactory scriptModelFactory;
    final JobModelFactory jobModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (ClientCreatedEventBodyModel) event.getBody();
        final var userId = body.getUserId();
        final var clientId = body.getId();
        return getClient(userId, clientId)
                .flatMap(client -> getPlayer(userId, client.getPlayerId())
                        .flatMap(player -> {
                            final var tenantId = player.getTenantId();
                            final var stageId = player.getStageId();
                            return getVersionId(tenantId, stageId)
                                    .flatMap(versionId -> syncScript(tenantId, versionId, client));
                        }))
                .replaceWith(true);
    }

    Uni<ClientModel> getClient(final Long userId, final Long clientId) {
        final var request = new GetClientRequest(userId, clientId);
        return userModule.getClientService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<PlayerModel> getPlayer(final Long userId, final Long playerId) {
        final var request = new GetPlayerRequest(userId, playerId);
        return userModule.getPlayerService().getPlayer(request)
                .map(GetPlayerResponse::getPlayer);
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
}
