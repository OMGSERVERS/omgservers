package com.omgservers.module.tenant.impl.operation.deleteProject;

import com.omgservers.model.event.body.ProjectDeletedEventBodyModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
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

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteProject(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final Long tenantId,
                                      final Long id) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, shard,
                """
                        delete from $schema.tab_tenant_project
                        where tenant_id = $1 and id = $2
                        """,
                Arrays.asList(tenantId, id),
                () -> new ProjectDeletedEventBodyModel(tenantId, id),
                () -> logModelFactory.create(String.format("Project was deleted, " +
                        "tenantId=%d, id=%d", tenantId, id))
        );
    }
}
