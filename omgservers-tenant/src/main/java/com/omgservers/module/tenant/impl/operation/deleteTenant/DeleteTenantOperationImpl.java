package com.omgservers.module.tenant.impl.operation.deleteTenant;

import com.omgservers.model.event.body.TenantDeletedEventBodyModel;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChange.ExecuteChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantOperationImpl implements DeleteTenantOperation {

    final ExecuteChangeOperation executeChangeOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteTenant(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long id) {
        return executeChangeOperation.executeChange(
                changeContext, sqlConnection, shard,
                """
                        delete from $schema.tab_tenant
                        where id = $1
                        """,
                Collections.singletonList(id),
                () -> new TenantDeletedEventBodyModel(id),
                () -> logModelFactory.create("Tenant was deleted, id=" + id)
        );
    }
}
