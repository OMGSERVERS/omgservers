package com.omgservers.module.tenant.impl.operation.deleteTenant;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.event.body.TenantDeletedEventBodyModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.module.tenant.impl.operation.selectTenant.SelectTenantOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
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

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final SelectTenantOperation selectTenantOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteTenant(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long id) {
        return selectTenantOperation.selectTenant(sqlConnection, shard, id)
                .flatMap(tenant -> executeChangeObjectOperation.executeChangeObject(
                        changeContext, sqlConnection, shard,
                        """
                                delete from $schema.tab_tenant
                                where id = $1
                                """,
                        Collections.singletonList(id),
                        () -> new TenantDeletedEventBodyModel(tenant),
                        () -> logModelFactory.create("Tenant was deleted, tenant=" + tenant)
                ))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(false);
    }
}
