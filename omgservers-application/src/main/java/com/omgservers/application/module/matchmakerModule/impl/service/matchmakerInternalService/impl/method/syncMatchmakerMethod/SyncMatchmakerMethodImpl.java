package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncMatchmakerMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.MatchmakerCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.matchmakerModule.impl.operation.upsertMatchmakerOperation.UpsertMatchmakerOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.SyncMatchmakerInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.SyncMatchmakerInternalResponse;
import com.omgservers.application.module.matchmakerModule.model.matchmaker.MatchmakerModel;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncMatchmakerMethodImpl implements SyncMatchmakerMethod {

    final InternalModule internalModule;

    final UpsertMatchmakerOperation upsertMatchmakerOperation;
    final CheckShardOperation checkShardOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerInternalRequest request) {
        SyncMatchmakerInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmaker = request.getMatchmaker();
                    return syncMatchmaker(shard.shard(), matchmaker);
                })
                .map(SyncMatchmakerInternalResponse::new);
    }

    Uni<Boolean> syncMatchmaker(final int shard, final MatchmakerModel matchmaker) {
        return pgPool.withTransaction(sqlConnection -> upsertMatchmakerOperation.upsertMatchmaker(sqlConnection, shard, matchmaker)
                .call(inserted -> {
                    if (inserted) {
                        final var id = matchmaker.getId();
                        final var tenantId = matchmaker.getTenantId();
                        final var stageId = matchmaker.getStageId();
                        final var eventBody = new MatchmakerCreatedEventBodyModel(id, tenantId, stageId);
                        final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, eventBody);
                        return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest)
                                .replaceWithVoid();
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                })
                .call(inserted -> {
                    final var syncLog = logModelFactory.create("Matchmaker was sync, matchmaker=" + matchmaker);
                    final var syncLogHelpRequest = new SyncLogHelpRequest(syncLog);
                    return internalModule.getLogHelpService().syncLog(syncLogHelpRequest);
                }));
    }
}
