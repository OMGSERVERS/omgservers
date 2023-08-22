package com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.deletePlayerMethod;

import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.userModule.impl.operation.deletePlayerOperation.DeletePlayerOperation;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.DeletePlayerInternalRequest;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeletePlayerMethodImpl implements DeletePlayerMethod {

    final DeletePlayerOperation deletePlayerOperation;
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<Void> deletePlayer(final DeletePlayerInternalRequest request) {
        DeletePlayerInternalRequest.validate(request);

        final var userId = request.getUserId();
        final var id = request.getId();
        return changeOperation.changeWithLog(request,
                        ((sqlConnection, shardModel) -> deletePlayerOperation
                                .deletePlayer(sqlConnection, shardModel.shard(), id)),
                        deleted -> {
                            if (deleted) {
                                return logModelFactory.create(String.format("Player was deleted, " +
                                        "userId=%d, id=%d", userId, id));
                            } else {
                                return null;
                            }
                        })
                //TODO: implement response with deleted flag
                .replaceWithVoid();
    }
}
