package com.omgservers.module.tenant.impl.operation.selectTenant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.operation.executeSelectObject.ExecuteSelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectTenantOperationImpl implements SelectTenantOperation {

    final ExecuteSelectObjectOperation executeSelectObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<TenantModel> selectTenant(final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long id) {
        return executeSelectObjectOperation.executeSelectObject(
                sqlConnection,
                shard,
                """
                        select id, created, modified, config
                        from $schema.tab_tenant
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "Tenant",
                this::createTenant);
    }

    TenantModel createTenant(Row row) {
        TenantModel tenant = new TenantModel();
        tenant.setId(row.getLong("id"));
        tenant.setCreated(row.getOffsetDateTime("created").toInstant());
        tenant.setModified(row.getOffsetDateTime("modified").toInstant());

        try {
            tenant.setConfig(objectMapper.readValue(row.getString("config"), TenantConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException("tenant config can't be parsed, tenant=" + tenant, e);
        }

        return tenant;
    }
}
