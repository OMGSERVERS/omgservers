package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncMatchMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.model.event.EventModelFactory;
import com.omgservers.application.module.internalModule.model.event.body.MatchCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.event.body.MatchUpdatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.matchmakerModule.impl.operation.upsertMatchOperation.UpsertMatchOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.MatchmakerInMemoryCache;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.SyncMatchInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.SyncMatchInternalResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncMatchMethodImpl implements SyncMatchMethod {

    final InternalModule internalModule;

    final UpsertMatchOperation upsertMatchOperation;
    final ChangeOperation changeOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;
    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncMatchInternalResponse> syncMatch(SyncMatchInternalRequest request) {
        SyncMatchInternalRequest.validate(request);

        final var match = request.getMatch();
        return changeOperation.changeWithEvent(request,
                        (sqlConnection, shardModel) -> upsertMatchOperation
                                .upsertMatch(sqlConnection, shardModel.shard(), match),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Match was created, match=" + match);
                            } else {
                                return logModelFactory.create("Match was updated, match=" + match);
                            }
                        },
                        inserted -> {
                            if (inserted) {
                                return new MatchCreatedEventBodyModel(match.getMatchmakerId(), match.getId());
                            } else {
                                return new MatchUpdatedEventBodyModel(match.getMatchmakerId(), match.getId());
                            }
                        }
                )
                .invoke(inserted -> matchmakerInMemoryCache.addMatch(match))
                .map(SyncMatchInternalResponse::new);
    }
}
