package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncMatchmakerMethod;

import com.omgservers.application.module.matchmakerModule.impl.operation.upsertMatchmakerOperation.UpsertMatchmakerOperation;
import com.omgservers.base.factory.LogModelFactory;
import com.omgservers.base.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithEventRequest;
import com.omgservers.dto.internalModule.ChangeWithEventResponse;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerRoutedRequest;
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

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncMatchmakerInternalResponse> syncMatchmaker(SyncMatchmakerRoutedRequest request) {
        SyncMatchmakerRoutedRequest.validate(request);

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
                .map(SyncMatchmakerInternalResponse::new);
    }
}
