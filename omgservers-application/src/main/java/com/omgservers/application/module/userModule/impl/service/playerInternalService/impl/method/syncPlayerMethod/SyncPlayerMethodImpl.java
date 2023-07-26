package com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.syncPlayerMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.EventCreatedEventBodyModel;
import com.omgservers.application.module.userModule.model.player.PlayerModel;
import com.omgservers.application.module.internalModule.model.event.body.PlayerCreatedEventBodyModel;
import com.omgservers.application.module.userModule.impl.operation.upsertPlayerOperation.UpsertPlayerOperation;
import com.omgservers.application.module.userModule.impl.operation.validatePlayerOperation.ValidatePlayerOperation;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.SyncPlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.response.SyncPlayerInternalResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncPlayerMethodImpl implements SyncPlayerMethod {

    final InternalModule internalModule;
    final ValidatePlayerOperation validatePlayerOperation;
    final CheckShardOperation checkShardOperation;
    final UpsertPlayerOperation upsertPlayerOperation;
    final PgPool pgPool;

    @Override
    public Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerInternalRequest request) {
        SyncPlayerInternalRequest.validate(request);

        final var player = request.getPlayer();
        final var user = player.getUserId();
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> validatePlayerOperation.validatePlayer(player))
                .flatMap(validatedProject -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> syncPlayer(shardModel.shard(), user, player))
                .map(SyncPlayerInternalResponse::new);
    }

    Uni<Boolean> syncPlayer(Integer shard, Long user, PlayerModel player) {
        return pgPool.withTransaction(sqlConnection ->
                upsertPlayerOperation.upsertPlayer(sqlConnection, shard, player)
                        .call(inserted -> {
                            if (inserted) {
                                final var id = player.getId();
                                final var stageId = player.getStageId();
                                final var eventBody = new PlayerCreatedEventBodyModel(user, stageId, id);
                                final var insertEventInternalRequest =
                                        new InsertEventHelpRequest(sqlConnection, eventBody);
                                return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest);
                            } else {
                                return Uni.createFrom().voidItem();
                            }
                        }));
    }
}
