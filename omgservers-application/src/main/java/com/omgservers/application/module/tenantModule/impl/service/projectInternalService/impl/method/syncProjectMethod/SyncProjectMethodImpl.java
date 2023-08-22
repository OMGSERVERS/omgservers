package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.syncProjectMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.event.body.ProjectUpdatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.tenantModule.impl.operation.upsertProjectOperation.UpsertProjectOperation;
import com.omgservers.application.module.tenantModule.impl.operation.validateProjectOperation.ValidateProjectOperation;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.SyncProjectInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.SyncProjectInternalResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
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
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncProjectInternalResponse> syncProject(SyncProjectInternalRequest request) {
        SyncProjectInternalRequest.validate(request);

        final var project = request.getProject();
        final var tenantId = project.getTenantId();
        validateProjectOperation.validateProject(project);

        return changeOperation.changeWithEvent(request,
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
                )
                .map(SyncProjectInternalResponse::new);
    }
}
