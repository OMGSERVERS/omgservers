package com.omgservers.module.context.impl.service.contextService.impl.method.handlePlayerSignedInEvent;

import com.omgservers.dto.context.HandlePlayerSignedInEventRequest;
import com.omgservers.dto.context.HandlePlayerSignedInEventResponse;
import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.GetStageShardedResponse;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.stage.StageModel;
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

        return getStageVersionId(tenantId, stageId)
                .flatMap(versionId -> createLuaPlayerContextOperation
                        .createLuaPlayerContext(userId, playerId, clientId)
                        .flatMap(luaPlayerContext -> handleLuaEventOperation
                                .handleLuaEvent(versionId, luaEvent, luaPlayerContext)))
                .replaceWith(new HandlePlayerSignedInEventResponse(true));
    }

    Uni<Long> getStageVersionId(final Long tenantId, final Long stageId) {
        final var request = new GetStageShardedRequest(tenantId, stageId);
        return tenantModule.getStageShardedService().getStage(request)
                .map(GetStageShardedResponse::getStage)
                .map(StageModel::getVersionId)
                .onItem().ifNull().failWith(new ServerSideConflictException(String
                        .format("no any stage's version wasn't deployed yet, " +
                                "tenantId=%d, stageId=%d", tenantId, stageId)));
    }
}
