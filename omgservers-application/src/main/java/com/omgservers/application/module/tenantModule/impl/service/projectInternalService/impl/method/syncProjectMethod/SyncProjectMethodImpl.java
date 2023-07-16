package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.syncProjectMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.EventCreatedEventBodyModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import com.omgservers.application.module.internalModule.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.application.module.tenantModule.impl.operation.upsertProjectOperation.UpsertProjectOperation;
import com.omgservers.application.module.tenantModule.impl.operation.validateProjectOperation.ValidateProjectOperation;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.SyncProjectInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.SyncProjectInternalResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncProjectMethodImpl implements SyncProjectMethod {

    final InternalModule internalModule;

    final ValidateProjectOperation validateProjectOperation;
    final UpsertProjectOperation upsertProjectOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncProjectInternalResponse> syncProject(SyncProjectInternalRequest request) {
        SyncProjectInternalRequest.validate(request);

        final var project = request.getProject();
        final var tenant = project.getTenant();
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> validateProjectOperation.validateProject(project))
                .flatMap(validatedProject -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> syncProject(shardModel.shard(), tenant, project))
                .map(SyncProjectInternalResponse::new);
    }

    Uni<Boolean> syncProject(Integer shard, UUID tenant, ProjectModel project) {
        return pgPool.withTransaction(sqlConnection ->
                upsertProjectOperation.upsertProject(sqlConnection, shard, project)
                        .call(inserted -> {
                            if (inserted) {
                                final var uuid = project.getUuid();
                                final var origin = ProjectCreatedEventBodyModel.createEvent(tenant, uuid);
                                final var event = EventCreatedEventBodyModel.createEvent(origin);
                                final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, event);
                                return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest);
                            } else {
                                return Uni.createFrom().voidItem();
                            }
                        }));
    }
}
