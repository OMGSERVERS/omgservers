package com.omgservers.service.module.tenant.impl.operation.upsertTenant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.TenantCreatedEventBodyModel;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertTenantOperationImpl implements UpsertTenantOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertTenant(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final TenantModel tenant) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant(
                            id, created, modified, deleted)
                        values($1, $2, $3, $4)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        tenant.getId(),
                        tenant.getCreated().atOffset(ZoneOffset.UTC),
                        tenant.getModified().atOffset(ZoneOffset.UTC),
                        tenant.getDeleted()
                ),
                () -> new TenantCreatedEventBodyModel(tenant.getId()),
                () -> logModelFactory.create("Tenant was inserted, tenant=" + tenant)
        );
    }
}
