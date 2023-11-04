package com.omgservers.service.module.tenant.impl.operation.deleteTenant;

import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.model.event.body.TenantDeletedEventBodyModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.module.tenant.impl.operation.selectTenant.SelectTenantOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
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

    final ChangeObjectOperation changeObjectOperation;
    final SelectTenantOperation selectTenantOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteTenant(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long id) {
        return selectTenantOperation.selectTenant(sqlConnection, shard, id)
                .flatMap(tenant -> changeObjectOperation.changeObject(
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
