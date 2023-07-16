package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteMatchmakerMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.EventCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.event.body.MatchmakerDeletedEventBodyModel;
import com.omgservers.application.module.matchmakerModule.impl.operation.deleteMatchmakerOperation.DeleteMatchmakerOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.DeleteMatchmakerInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.DeleteMatchmakerInternalResponse;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchmakerMethodImpl implements DeleteMatchmakerMethod {

    final InternalModule internalModule;

    final DeleteMatchmakerOperation deleteMatchmakerOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerInternalRequest request) {
        DeleteMatchmakerInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var uuid = request.getUuid();
                    return deleteMatchmaker(shard.shard(), uuid);
                })
                .map(DeleteMatchmakerInternalResponse::new);
    }

    Uni<Boolean> deleteMatchmaker(final int shard,
                                  final UUID uuid) {
        return pgPool.withTransaction(sqlConnection -> deleteMatchmakerOperation
                .deleteMatchmaker(sqlConnection, shard, uuid)
                .call(deleted -> {
                    if (deleted) {
                        final var origin = MatchmakerDeletedEventBodyModel.createEvent(uuid);
                        final var event = EventCreatedEventBodyModel.createEvent(origin);
                        final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, event);
                        return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest);
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                }));
    }
}
