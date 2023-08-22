package com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.syncPlayerMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.model.event.body.PlayerCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.event.body.PlayerUpdatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.userModule.impl.operation.upsertPlayerOperation.UpsertPlayerOperation;
import com.omgservers.application.module.userModule.impl.operation.validatePlayerOperation.ValidatePlayerOperation;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.SyncPlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.response.SyncPlayerInternalResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
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
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerInternalRequest request) {
        SyncPlayerInternalRequest.validate(request);

        final var player = request.getPlayer();
        final var userId = player.getUserId();
        return changeOperation.changeWithEvent(request,
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
                )
                .map(SyncPlayerInternalResponse::new);
    }
}
