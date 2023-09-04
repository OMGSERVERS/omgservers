package com.omgservers.module.user.impl.service.playerShardedService.impl.method.syncPlayer;

import com.omgservers.ChangeWithEventRequest;
import com.omgservers.ChangeWithEventResponse;
import com.omgservers.dto.user.SyncPlayerShardedResponse;
import com.omgservers.dto.user.SyncPlayerShardedRequest;
import com.omgservers.model.event.body.PlayerCreatedEventBodyModel;
import com.omgservers.model.event.body.PlayerUpdatedEventBodyModel;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.user.impl.operation.upsertPlayer.UpsertPlayerOperation;
import com.omgservers.module.user.impl.operation.validatePlayer.ValidatePlayerOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncPlayerMethodImpl implements SyncPlayerMethod {

    final InternalModule internalModule;

    final ValidatePlayerOperation validatePlayerOperation;
    final UpsertPlayerOperation upsertPlayerOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncPlayerShardedResponse> syncPlayer(SyncPlayerShardedRequest request) {
        SyncPlayerShardedRequest.validate(request);

        final var player = request.getPlayer();
        final var userId = player.getUserId();
        return internalModule.getChangeService().changeWithEvent(new ChangeWithEventRequest(request,
                        (sqlConnection, shardModel) -> upsertPlayerOperation
                                .upsertPlayer(sqlConnection, shardModel.shard(), player),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Player was created, player=" + player);
                            } else {
                                return logModelFactory.create("Player was updated, player=" + player);
                            }
                        },
                        inserted -> {
                            final var stageId = player.getStageId();
                            final var id = player.getId();
                            if (inserted) {
                                return new PlayerCreatedEventBodyModel(userId, stageId, id);
                            } else {
                                return new PlayerUpdatedEventBodyModel(userId, stageId, id);
                            }
                        }
                ))
                .map(ChangeWithEventResponse::getResult)
                .map(SyncPlayerShardedResponse::new);
    }
}
