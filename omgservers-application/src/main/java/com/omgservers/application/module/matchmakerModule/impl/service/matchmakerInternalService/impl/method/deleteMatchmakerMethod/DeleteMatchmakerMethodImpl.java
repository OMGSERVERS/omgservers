package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteMatchmakerMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.model.event.body.MatchmakerDeletedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.matchmakerModule.impl.operation.deleteMatchmakerOperation.DeleteMatchmakerOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.DeleteMatchmakerInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.DeleteMatchmakerInternalResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchmakerMethodImpl implements DeleteMatchmakerMethod {

    final InternalModule internalModule;

    final DeleteMatchmakerOperation deleteMatchmakerOperation;
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerInternalRequest request) {
        DeleteMatchmakerInternalRequest.validate(request);

        final var id = request.getId();
        return changeOperation.changeWithEvent(request,
                        (sqlConnection, shardModel) -> deleteMatchmakerOperation
                                .deleteMatchmaker(sqlConnection, shardModel.shard(), id),
                        deleted -> {
                            if (deleted) {
                                return logModelFactory.create("Matchmaker was deleted, matchmakerId=" + id);
                            } else {
                                return null;
                            }
                        },
                        deleted -> {
                            if (deleted) {
                                return new MatchmakerDeletedEventBodyModel(id);
                            } else {
                                return null;
                            }
                        }
                )
                .map(DeleteMatchmakerInternalResponse::new);
    }
}
