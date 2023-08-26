package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.deleteProjectMethod;

import com.omgservers.application.module.tenantModule.impl.operation.deleteProjectOperation.DeleteProjectOperation;
import com.omgservers.base.factory.LogModelFactory;
import com.omgservers.base.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithEventRequest;
import com.omgservers.dto.internalModule.ChangeWithEventResponse;
import com.omgservers.dto.tenantModule.DeleteProjectRoutedRequest;
import com.omgservers.model.event.body.ProjectDeletedEventBodyModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteProjectMethodImpl implements DeleteProjectMethod {

    final InternalModule internalModule;

    final DeleteProjectOperation deleteProjectOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<Void> deleteProject(final DeleteProjectRoutedRequest request) {
        DeleteProjectRoutedRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();
        return internalModule.getChangeService().changeWithEvent(new ChangeWithEventRequest(request,
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
                        }))
                .map(ChangeWithEventResponse::getResult)
                //TODO implement response with deleted flag
                .replaceWithVoid();
    }
}
