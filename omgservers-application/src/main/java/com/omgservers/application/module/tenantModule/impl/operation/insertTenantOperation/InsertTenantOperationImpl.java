package com.omgservers.application.module.tenantModule.impl.operation.insertTenantOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideInternalException;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InsertTenantOperationImpl implements InsertTenantOperation {

    static private final String sql = """
            insert into $schema.tab_tenant(id, created, modified, config)
            values($1, $2, $3, $4)
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> insertTenant(final SqlConnection sqlConnection,
                                  final int shard,
                                  final TenantModel tenant) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (tenant == null) {
            throw new ServerSideBadRequestException("tenant is null");
        }

        return insertQuery(sqlConnection, shard, tenant)
                .invoke(voidItem -> log.info("Tenant was inserted, tenant={}", tenant))
                .onFailure(PgException.class)
                .transform(t -> new ServerSideConflictException(String
                        .format("unhandled PgException, %s, tenant=%s", t.getMessage(), tenant)));
    }

    Uni<Void> insertQuery(SqlConnection sqlConnection, int shard, TenantModel tenant) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            var configString = objectMapper.writeValueAsString(tenant.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.of(
                            tenant.getId(),
                            tenant.getCreated().atOffset(ZoneOffset.UTC),
                            tenant.getModified().atOffset(ZoneOffset.UTC),
                            configString))
                    .replaceWithVoid();
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
