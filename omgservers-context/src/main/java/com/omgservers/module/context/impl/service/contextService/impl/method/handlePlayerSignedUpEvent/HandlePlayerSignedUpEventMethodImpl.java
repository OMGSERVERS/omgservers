package com.omgservers.module.context.impl.service.contextService.impl.method.handlePlayerSignedUpEvent;

import com.omgservers.dto.context.HandlePlayerSignedUpEventRequest;
import com.omgservers.dto.context.HandlePlayerSignedUpEventResponse;
import com.omgservers.dto.tenant.GetStageVersionIdRequest;
import com.omgservers.dto.tenant.GetStageVersionIdResponse;
import com.omgservers.module.context.impl.luaEvent.player.LuaPlayerSignedUpEvent;
import com.omgservers.module.context.impl.operation.createLuaPlayerContext.CreateLuaPlayerContextOperation;
import com.omgservers.module.context.impl.operation.handleLuaEvent.HandleLuaEventOperation;
import com.omgservers.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandlePlayerSignedUpEventMethodImpl implements HandlePlayerSignedUpEventMethod {

    final TenantModule tenantModule;

    final CreateLuaPlayerContextOperation createLuaPlayerContextOperation;
    final HandleLuaEventOperation handleLuaEventOperation;

    @Override
    public Uni<HandlePlayerSignedUpEventResponse> handleLuaPlayerSignedUpEvent(final HandlePlayerSignedUpEventRequest request) {
        HandlePlayerSignedUpEventRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var userId = request.getUserId();
        final var playerId = request.getPlayerId();
        final var clientId = request.getClientId();

        final var getCurrentVersionIdShardedRequest = new GetStageVersionIdRequest(tenantId, stageId);
        return tenantModule.getVersionService().getStageVersionId(getCurrentVersionIdShardedRequest)
                .map(GetStageVersionIdResponse::getVersionId)
                .flatMap(versionId -> createLuaPlayerContextOperation
                        .createLuaPlayerContext(userId, playerId, clientId)
                        .flatMap(luaPlayerContext -> {
                            final var luaEvent = new LuaPlayerSignedUpEvent(userId, playerId, clientId);
                            return handleLuaEventOperation.handleLuaEvent(tenantId, versionId, luaEvent, luaPlayerContext);
                        }))
                .replaceWith(new HandlePlayerSignedUpEventResponse(true));
    }
}
