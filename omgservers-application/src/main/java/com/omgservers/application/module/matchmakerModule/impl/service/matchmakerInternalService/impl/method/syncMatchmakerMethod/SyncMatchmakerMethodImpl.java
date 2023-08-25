package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncMatchmakerMethod;

import com.omgservers.base.factory.LogModelFactory;
import com.omgservers.base.InternalModule;
import com.omgservers.application.module.matchmakerModule.impl.operation.upsertMatchmakerOperation.UpsertMatchmakerOperation;
import com.omgservers.base.impl.operation.changeOperation.ChangeOperation;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalRequest;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalResponse;
import com.omgservers.model.event.body.MatchmakerCreatedEventBodyModel;
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
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerInternalRequest request) {
        SyncMatchmakerInternalRequest.validate(request);

        final var matchmaker = request.getMatchmaker();
        return changeOperation.changeWithEvent(request,
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
                )
                .map(SyncMatchmakerInternalResponse::new);
    }
}
