package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.syncMatchmaker;

import com.omgservers.dto.internal.ChangeWithEventRequest;
import com.omgservers.dto.internal.ChangeWithEventResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakerShardedRequest;
import com.omgservers.model.event.body.MatchmakerCreatedEventBodyModel;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.factory.LogModelFactory;
import com.omgservers.module.matchmaker.impl.operation.upsertMatchmaker.UpsertMatchmakerOperation;
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

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncMatchmakerShardResponse> syncMatchmaker(SyncMatchmakerShardedRequest request) {
        SyncMatchmakerShardedRequest.validate(request);

        final var matchmaker = request.getMatchmaker();
        return internalModule.getChangeService().changeWithEvent(new ChangeWithEventRequest(request,
                        (sqlConnection, shardModel) -> upsertMatchmakerOperation.
                                upsertMatchmaker(sqlConnection, shardModel.shard(), matchmaker),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Matchmaker was created, matchmaker=" + matchmaker);
                            } else {
                                return logModelFactory.create("Matchmaker was updated, matchmaker=" + matchmaker);
                            }
                        },
                        inserted -> {
                            final var id = matchmaker.getId();
                            final var tenantId = matchmaker.getTenantId();
                            final var stageId = matchmaker.getStageId();
                            if (inserted) {
                                return new MatchmakerCreatedEventBodyModel(id, tenantId, stageId);
                            } else {
                                return new MatchmakerCreatedEventBodyModel(id, tenantId, stageId);
                            }
                        }
                ))
                .map(ChangeWithEventResponse::getResult)
                .map(SyncMatchmakerShardResponse::new);
    }
}
