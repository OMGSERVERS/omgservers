package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.syncProjectMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.tenantModule.impl.operation.upsertProjectOperation.UpsertProjectOperation;
import com.omgservers.application.module.tenantModule.impl.operation.validateProjectOperation.ValidateProjectOperation;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.SyncProjectInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.SyncProjectInternalResponse;
import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncProjectMethodImpl implements SyncProjectMethod {

    final InternalModule internalModule;

    final ValidateProjectOperation validateProjectOperation;
    final UpsertProjectOperation upsertProjectOperation;
    final CheckShardOperation checkShardOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncProjectInternalResponse> syncProject(SyncProjectInternalRequest request) {
        SyncProjectInternalRequest.validate(request);

        final var project = request.getProject();
        final var tenant = project.getTenantId();
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> validateProjectOperation.validateProject(project))
                .flatMap(validatedProject -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> syncProject(shardModel.shard(), tenant, project))
                .map(SyncProjectInternalResponse::new);
    }

    Uni<Boolean> syncProject(Integer shard, Long tenantId, ProjectModel project) {
        return pgPool.withTransaction(sqlConnection ->
                upsertProjectOperation.upsertProject(sqlConnection, shard, project)
                        .call(inserted -> {
                            if (inserted) {
                                final var id = project.getId();
                                final var eventBody = new ProjectCreatedEventBodyModel(tenantId, id);
                                final var insertEventInternalRequest =
                                        new InsertEventHelpRequest(sqlConnection, eventBody);
                                return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest);
                            } else {
                                return Uni.createFrom().voidItem();
                            }
                        })
                        .call(inserted -> {
                            final LogModel syncLog;
                            if (inserted) {
                                syncLog = logModelFactory.create("Project was created, project=" + project);
                            } else {
                                syncLog = logModelFactory.create("Project was updated, project=" + project);
                            }
                            final var syncLogHelpRequest = new SyncLogHelpRequest(syncLog);
                            return internalModule.getLogHelpService().syncLog(syncLogHelpRequest);
                        }));
    }
}
