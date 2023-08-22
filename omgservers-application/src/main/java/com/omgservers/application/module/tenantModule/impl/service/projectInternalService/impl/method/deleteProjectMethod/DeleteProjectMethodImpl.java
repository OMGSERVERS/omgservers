package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.deleteProjectMethod;

import com.omgservers.application.module.internalModule.model.event.body.ProjectDeletedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.tenantModule.impl.operation.deleteProjectOperation.DeleteProjectOperation;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.DeleteProjectInternalRequest;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteProjectMethodImpl implements DeleteProjectMethod {

    final DeleteProjectOperation deleteProjectOperation;
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<Void> deleteProject(final DeleteProjectInternalRequest request) {
        DeleteProjectInternalRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();
        return changeOperation.changeWithEvent(request,
                        ((sqlConnection, shardModel) -> deleteProjectOperation
                                .deleteProject(sqlConnection, shardModel.shard(), id)),
                        deleted -> {
                            if (deleted) {
                                return logModelFactory.create(String.format("Project was deleted, tenantId=%d, id=%d", tenantId, id));
                            } else {
                                return null;
                            }
                        },
                        deleted -> {
                            if (deleted) {
                                return new ProjectDeletedEventBodyModel(tenantId, id);
                            } else {
                                return null;
                            }
                        })
                //TODO implement response with deleted flag
                .replaceWithVoid();
    }
}
