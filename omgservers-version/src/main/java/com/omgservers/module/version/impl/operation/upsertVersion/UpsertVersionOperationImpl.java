package com.omgservers.module.version.impl.operation.upsertVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.version.VersionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UpsertVersionOperationImpl implements UpsertVersionOperation {

    static private final String sql = """
            insert into $schema.tab_version(id, created, modified, tenant_id, stage_id, stage_config, source_code, bytecode, status, errors)
            values($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)
            on conflict (id) do
            nothing
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertVersion(final SqlConnection sqlConnection,
                                      final int shard,
                                      final VersionModel version) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (version == null) {
            throw new ServerSideBadRequestException("version is null");
        }

        return upsertQuery(sqlConnection, shard, version)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Version was inserted, version={}", version);
                    } else {
                        log.info("Version was updated, version={}", version);
                    }
                });
    }

    Uni<Boolean> upsertQuery(SqlConnection sqlConnection, int shard, VersionModel version) {
        try {
            final var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            final var stageConfig = objectMapper.writeValueAsString(version.getStageConfig());
            final var sourceCode = objectMapper.writeValueAsString(version.getSourceCode());
            final var bytecode = objectMapper.writeValueAsString(version.getBytecode());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(Arrays.asList(
                            version.getId(),
                            version.getCreated().atOffset(ZoneOffset.UTC),
                            version.getModified().atOffset(ZoneOffset.UTC),
                            version.getTenantId(),
                            version.getStageId(),
                            stageConfig,
                            sourceCode,
                            bytecode,
                            version.getStatus(),
                            version.getErrors()
                    )))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
