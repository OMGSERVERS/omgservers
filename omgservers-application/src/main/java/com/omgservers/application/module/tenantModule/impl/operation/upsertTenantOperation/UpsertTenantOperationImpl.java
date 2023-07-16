package com.omgservers.application.module.tenantModule.impl.operation.upsertTenantOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.time.ZoneOffset;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertTenantOperationImpl implements UpsertTenantOperation {

    static private final String sql = """
            insert into $schema.tab_tenant(created, modified, uuid, config)
            values($1, $2, $3, $4)
            on conflict (uuid) do
            update set modified = $2, config = $4
            returning xmax::text::int = 0 as inserted
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertTenant(final SqlConnection sqlConnection,
                                     final int shard,
                                     final TenantModel tenant) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (tenant == null) {
            throw new ServerSideBadRequestException("tenant is null");
        }

        return upsertQuery(sqlConnection, shard, tenant)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Tenant was inserted, shard={}, tenant={}", shard, tenant);
                    } else {
                        log.info("Tenant was updated, shard={}, tenant={}", shard, tenant);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> new ServerSideConflictException(String
                        .format("unhandled PgException, %s, tenant=%s", t.getMessage(), tenant)));
    }

    Uni<Boolean> upsertQuery(SqlConnection sqlConnection, int shard, TenantModel tenant) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            var configString = objectMapper.writeValueAsString(tenant.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.of(
                            tenant.getCreated().atOffset(ZoneOffset.UTC),
                            tenant.getModified().atOffset(ZoneOffset.UTC),
                            tenant.getUuid(),
                            configString))
                    .map(rowSet -> rowSet.iterator().next().getBoolean("inserted"));
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
