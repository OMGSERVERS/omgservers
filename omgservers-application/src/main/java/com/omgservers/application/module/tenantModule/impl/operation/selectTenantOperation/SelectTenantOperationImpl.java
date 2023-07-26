package com.omgservers.application.module.tenantModule.impl.operation.selectTenantOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.tenantModule.model.tenant.TenantConfigModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectTenantOperationImpl implements SelectTenantOperation {

    static private final String sql = """
            select id, created, modified, config
            from $schema.tab_tenant
            where id = $1
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<TenantModel> selectTenant(final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long id) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(id))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        try {
                            final var tenant = createTenant(iterator.next());
                            log.info("Tenant was found, tenant={}", tenant);
                            return tenant;
                        } catch (IOException e) {
                            throw new ServerSideConflictException("tenant can't be parsed, id=" + id, e);
                        }
                    } else {
                        throw new ServerSideNotFoundException("tenant was not found, id=" + id);
                    }
                });
    }

    TenantModel createTenant(Row row) throws IOException {
        TenantModel tenant = new TenantModel();
        tenant.setId(row.getLong("id"));
        tenant.setCreated(row.getOffsetDateTime("created").toInstant());
        tenant.setModified(row.getOffsetDateTime("modified").toInstant());
        tenant.setConfig(objectMapper.readValue(row.getString("config"), TenantConfigModel.class));
        return tenant;
    }
}
