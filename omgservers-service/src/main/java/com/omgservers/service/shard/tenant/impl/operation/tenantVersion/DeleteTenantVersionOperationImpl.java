package com.omgservers.service.shard.tenant.impl.operation.tenantVersion;

import com.omgservers.service.event.body.module.tenant.TenantVersionDeletedEventBodyModel;
import com.omgservers.service.factory.system.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantVersionOperationImpl implements DeleteTenantVersionOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectTenantVersionOperation selectTenantVersionOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final Long tenantId,
                                final Long id) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        update $slot.tab_tenant_version
                        set modified = $3, deleted = true
                        where tenant_id = $1 and id = $2 and deleted = false
                        """,
                List.of(
                        tenantId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new TenantVersionDeletedEventBodyModel(tenantId, id),
                () -> null
        );
    }
}
