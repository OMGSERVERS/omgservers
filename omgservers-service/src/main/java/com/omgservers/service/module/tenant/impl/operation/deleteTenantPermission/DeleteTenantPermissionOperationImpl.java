package com.omgservers.service.module.tenant.impl.operation.deleteTenantPermission;

import com.omgservers.model.event.body.ProjectDeletedEventBodyModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantPermissionOperationImpl implements DeleteTenantPermissionOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteTenantPermission(final ChangeContext<?> changeContext,
                                               final SqlConnection sqlConnection,
                                               final int shard,
                                               final Long tenantId,
                                               final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_tenant_permission
                        set modified = $3, deleted = true
                        where tenant_id = $1 and id = $2 and deleted = false
                        """,
                Arrays.asList(
                        tenantId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new ProjectDeletedEventBodyModel(tenantId, id),
                () -> logModelFactory.create(String.format("Tenant permission was deleted, " +
                        "tenantId=%d, id=%d", tenantId, id))
        );
    }
}
