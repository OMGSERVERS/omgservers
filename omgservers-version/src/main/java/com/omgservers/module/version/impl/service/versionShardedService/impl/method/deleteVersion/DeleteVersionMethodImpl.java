package com.omgservers.module.version.impl.service.versionShardedService.impl.method.deleteVersion;

import com.omgservers.module.version.impl.operation.deleteVersion.DeleteVersionOperation;
import com.omgservers.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internal.ChangeWithEventRequest;
import com.omgservers.dto.internal.ChangeWithEventResponse;
import com.omgservers.dto.version.DeleteVersionShardedRequest;
import com.omgservers.dto.version.DeleteVersionShardedResponse;
import com.omgservers.model.event.body.VersionDeletedEventBodyModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteVersionMethodImpl implements DeleteVersionMethod {

    final InternalModule internalModule;

    final DeleteVersionOperation deleteVersionOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteVersionShardedResponse> deleteVersion(final DeleteVersionShardedRequest request) {
        DeleteVersionShardedRequest.validate(request);

        final var id = request.getId();
        return internalModule.getChangeService().changeWithEvent(new ChangeWithEventRequest(request,
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
                ))
                .map(ChangeWithEventResponse::getResult)
                .map(DeleteVersionShardedResponse::new);
    }
}
