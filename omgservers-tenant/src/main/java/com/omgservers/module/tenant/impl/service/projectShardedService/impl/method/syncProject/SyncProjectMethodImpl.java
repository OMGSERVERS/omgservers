package com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.syncProject;

import com.omgservers.module.tenant.impl.operation.upsertProject.UpsertProjectOperation;
import com.omgservers.module.tenant.impl.operation.validateProject.ValidateProjectOperation;
import com.omgservers.module.internal.impl.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithEventRequest;
import com.omgservers.dto.internalModule.ChangeWithEventResponse;
import com.omgservers.dto.tenantModule.SyncProjectShardRequest;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import com.omgservers.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.model.event.body.ProjectUpdatedEventBodyModel;
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

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncProjectInternalResponse> syncProject(SyncProjectShardRequest request) {
        SyncProjectShardRequest.validate(request);

        final var project = request.getProject();
        final var tenantId = project.getTenantId();
        validateProjectOperation.validateProject(project);

        return internalModule.getChangeService().changeWithEvent(new ChangeWithEventRequest(request,
                        (sqlConnection, shardModel) -> upsertProjectOperation
                                .upsertProject(sqlConnection, shardModel.shard(), project),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Project was created, project=" + project);
                            } else {
                                return logModelFactory.create("Project was updated, project=" + project);
                            }
                        },
                        inserted -> {
                            final var id = project.getId();
                            if (inserted) {
                                return new ProjectCreatedEventBodyModel(tenantId, id);
                            } else {
                                return new ProjectUpdatedEventBodyModel(tenantId, id);
                            }
                        }
                ))
                .map(ChangeWithEventResponse::getResult)
                .map(SyncProjectInternalResponse::new);
    }
}
