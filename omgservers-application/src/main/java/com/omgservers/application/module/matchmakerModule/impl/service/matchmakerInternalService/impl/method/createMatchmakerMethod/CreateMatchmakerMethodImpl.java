package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.createMatchmakerMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.EventCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.event.body.MatchmakerCreatedEventBodyModel;
import com.omgservers.application.module.matchmakerModule.impl.operation.insertMatchmakerOperation.InsertMatchmakerOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.CreateMatchmakerInternalRequest;
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
class CreateMatchmakerMethodImpl implements CreateMatchmakerMethod {

    final InternalModule internalModule;

    final InsertMatchmakerOperation insertMatchmakerOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<Void> createMatchmaker(CreateMatchmakerInternalRequest request) {
        CreateMatchmakerInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmaker = request.getMatchmaker();
                    return createMatchmaker(shard.shard(), matchmaker);
                });
    }

    Uni<Void> createMatchmaker(final int shard, final MatchmakerModel matchmaker) {
        return pgPool.withTransaction(sqlConnection -> insertMatchmakerOperation.insertMatchmaker(sqlConnection, shard, matchmaker)
                .call(voidItem -> {
                    final var uuid = matchmaker.getUuid();
                    final var tenant = matchmaker.getTenant();
                    final var stage = matchmaker.getStage();
                    final var origin = MatchmakerCreatedEventBodyModel.createEvent(uuid, tenant, stage);
                    final var event = EventCreatedEventBodyModel.createEvent(origin);
                    final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, event);
                    return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest)
                            .replaceWithVoid();
                }));
    }
}
