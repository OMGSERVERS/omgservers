package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.createVersionMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.EventCreatedEventBodyModel;
import com.omgservers.application.module.versionModule.impl.operation.insertVersionOperation.InsertVersionOperation;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.module.versionModule.model.VersionModel;
import com.omgservers.application.module.internalModule.model.event.body.VersionCreatedEventBodyModel;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.CreateVersionInternalRequest;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateVersionMethodImpl implements CreateVersionMethod {

    final InternalModule internalModule;
    final CheckShardOperation checkShardOperation;
    final InsertVersionOperation insertVersionOperation;
    final PgPool pgPool;

    @Override
    public Uni<Void> createVersion(CreateVersionInternalRequest request) {
        CreateVersionInternalRequest.validate(request);

        final var version = request.getVersion();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> createVersion(shard.shard(), version));
    }

    Uni<Void> createVersion(Integer shard, VersionModel version) {
        return pgPool.withTransaction(sqlConnection ->
                        insertVersionOperation.insertVersion(sqlConnection, shard, version)
                                .flatMap(result -> {
                                    final var id = version.getId();
                                    final var tenantId = version.getTenantId();
                                    final var stageId = version.getStageId();
                                    final var eventBody = new VersionCreatedEventBodyModel(tenantId, stageId, id);
                                    final var insertEventInternalRequest =
                                            new InsertEventHelpRequest(sqlConnection, eventBody);
                                    return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest);
                                }))
                .replaceWithVoid();
    }
}
