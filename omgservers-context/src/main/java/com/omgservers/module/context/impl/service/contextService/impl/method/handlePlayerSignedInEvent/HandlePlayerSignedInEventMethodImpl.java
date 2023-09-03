package com.omgservers.module.context.impl.service.contextService.impl.method.handlePlayerSignedInEvent;

import com.omgservers.dto.context.HandlePlayerSignedInEventRequest;
import com.omgservers.dto.context.HandlePlayerSignedInEventResponse;
import com.omgservers.dto.tenant.GetStageVersionIdShardedRequest;
import com.omgservers.dto.tenant.GetStageVersionIdShardedResponse;
import com.omgservers.module.context.impl.luaEvent.player.LuaPlayerSignedInEvent;
import com.omgservers.module.context.impl.operation.createLuaGlobals.CreateLuaGlobalsOperation;
import com.omgservers.module.context.impl.operation.createLuaPlayerContext.CreateLuaPlayerContextOperation;
import com.omgservers.module.context.impl.operation.handleLuaEvent.HandleLuaEventOperation;
import com.omgservers.module.lua.impl.service.luaService.LuaService;
import com.omgservers.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandlePlayerSignedInEventMethodImpl implements HandlePlayerSignedInEventMethod {

    final TenantModule tenantModule;

    final LuaService luaService;

    final CreateLuaPlayerContextOperation createLuaPlayerContextOperation;
    final HandleLuaEventOperation handleLuaEventOperation;

    final CreateLuaGlobalsOperation createLuaGlobalsOperation;


    @Override
    public Uni<HandlePlayerSignedInEventResponse> handleLuaPlayerSignedInEvent(final HandlePlayerSignedInEventRequest request) {
        HandlePlayerSignedInEventRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var userId = request.getUserId();
        final var playerId = request.getPlayerId();
        final var clientId = request.getClientId();

        final var luaEvent = new LuaPlayerSignedInEvent(userId, playerId, clientId);

        final var getCurrentVersionIdShardedRequest = new GetStageVersionIdShardedRequest(tenantId, stageId);
        return tenantModule.getVersionShardedService().getStageVersionId(getCurrentVersionIdShardedRequest)
                .map(GetStageVersionIdShardedResponse::getVersionId)
                .flatMap(versionId -> createLuaPlayerContextOperation
                        .createLuaPlayerContext(userId, playerId, clientId)
                        .flatMap(luaPlayerContext -> handleLuaEventOperation
                                .handleLuaEvent(tenantId, versionId, luaEvent, luaPlayerContext)))
                .replaceWith(new HandlePlayerSignedInEventResponse(true));
    }
}
