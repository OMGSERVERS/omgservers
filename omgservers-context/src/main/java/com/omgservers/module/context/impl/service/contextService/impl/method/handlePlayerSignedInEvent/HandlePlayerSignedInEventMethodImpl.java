package com.omgservers.module.context.impl.service.contextService.impl.method.handlePlayerSignedInEvent;

import com.omgservers.dto.context.CreateLuaInstanceForPlayerEventsRequest;
import com.omgservers.dto.context.HandlePlayerSignedInEventRequest;
import com.omgservers.dto.context.HandlePlayerSignedInEventResponse;
import com.omgservers.dto.tenant.GetStageVersionIdRequest;
import com.omgservers.dto.tenant.GetStageVersionIdResponse;
import com.omgservers.module.context.ContextModule;
import com.omgservers.module.context.impl.luaEvent.player.LuaPlayerSignedInEvent;
import com.omgservers.module.context.impl.operation.handlePlayerLuaEvent.HandlePlayerLuaEventOperation;
import com.omgservers.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandlePlayerSignedInEventMethodImpl implements HandlePlayerSignedInEventMethod {

    final ContextModule contextModule;
    final TenantModule tenantModule;

    final HandlePlayerLuaEventOperation handlePlayerLuaEventOperation;

    @Override
    public Uni<HandlePlayerSignedInEventResponse> handleLuaPlayerSignedInEvent(final HandlePlayerSignedInEventRequest request) {
        HandlePlayerSignedInEventRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var userId = request.getUserId();
        final var playerId = request.getPlayerId();
        final var clientId = request.getClientId();

        return getVersionId(tenantId, stageId)
                .flatMap(versionId -> {
                    final var createLuaInstanceForPlayerEventsRequest =
                            new CreateLuaInstanceForPlayerEventsRequest(tenantId, versionId, userId, playerId, clientId);
                    return contextModule.getContextService()
                            .createLuaInstanceForPlayerEvents(createLuaInstanceForPlayerEventsRequest);
                })
                .flatMap(ignored -> {
                    final var luaEvent = new LuaPlayerSignedInEvent(userId, playerId, clientId);
                    return handlePlayerLuaEventOperation.handlePlayerLuaEvent(clientId, luaEvent);
                })
                .replaceWith(new HandlePlayerSignedInEventResponse(true));
    }

    Uni<Long> getVersionId(final Long tenantId, final Long stageId) {
        final var getCurrentVersionIdShardedRequest = new GetStageVersionIdRequest(tenantId, stageId);
        return tenantModule.getVersionService().getStageVersionId(getCurrentVersionIdShardedRequest)
                .map(GetStageVersionIdResponse::getVersionId);
    }
}
