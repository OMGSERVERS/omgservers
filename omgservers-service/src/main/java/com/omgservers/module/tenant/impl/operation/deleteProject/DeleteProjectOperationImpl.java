package com.omgservers.module.tenant.impl.operation.deleteProject;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.event.body.ProjectDeletedEventBodyModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.module.tenant.impl.operation.selectProject.SelectProjectOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteProjectOperationImpl implements DeleteProjectOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectProjectOperation selectProjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteProject(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final Long tenantId,
                                      final Long id) {
        return selectProjectOperation.selectProject(sqlConnection, shard, tenantId, id)
                .flatMap(project -> changeObjectOperation.changeObject(
                        changeContext, sqlConnection, shard,
                        """
                                delete from $schema.tab_tenant_project
                                where tenant_id = $1 and id = $2
                                """,
                        Arrays.asList(tenantId, id),
                        () -> new ProjectDeletedEventBodyModel(project),
                        () -> logModelFactory.create("Project was deleted, project=" + project)
                ))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(false);
    }
}
