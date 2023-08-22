package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.deleteVersionMethod;

import com.omgservers.application.module.internalModule.model.event.body.VersionDeletedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.versionModule.impl.operation.deleteVersionOperation.DeleteVersionOperation;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.DeleteVersionInternalRequest;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteVersionMethodImpl implements DeleteVersionMethod {

    final DeleteVersionOperation deleteVersionOperation;
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<Void> deleteVersion(final DeleteVersionInternalRequest request) {
        DeleteVersionInternalRequest.validate(request);

        final var id = request.getId();
        return changeOperation.changeWithEvent(request,
                        (sqlConnection, shardModel) -> deleteVersionOperation
                                .deleteVersion(sqlConnection, shardModel.shard(), id),
                        deleted -> {
                            if (deleted) {
                                return logModelFactory.create("Version was deleted, id=" + id);
                            } else {
                                return null;
                            }
                        },
                        deleted -> {
                            if (deleted) {
                                return new VersionDeletedEventBodyModel(id);
                            } else {
                                return null;
                            }
                        }
                )
                //TODO: add response with deleted field
                .replaceWithVoid();
    }
}
