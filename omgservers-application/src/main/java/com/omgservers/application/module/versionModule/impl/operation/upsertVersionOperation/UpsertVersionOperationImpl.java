package com.omgservers.application.module.versionModule.impl.operation.upsertVersionOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.versionModule.model.VersionModel;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideInternalException;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UpsertVersionOperationImpl implements UpsertVersionOperation {

    static private final String sql = """
            insert into $schema.tab_version(created, modified, uuid, tenant, stage, stage_config, source_code, bytecode, status, errors)
            values($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)
            on conflict (uuid) do
            update set modified = $2, tenant = $4, stage = $5, stage_config = $6, source_code = $7, bytecode = $8, status = $9, errors = $10
            returning id, xmax::text::int = 0 as inserted
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Tuple2<Long, Boolean>> upsertVersion(final SqlConnection sqlConnection,
                                                    final int shard,
                                                    final VersionModel version) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (version == null) {
            throw new ServerSideBadRequestException("version is null");
        }

        return upsertQuery(sqlConnection, shard, version)
                .invoke(result -> {
                    final var id = result.getItem1();
                    final var inserted = result.getItem2();
                    if (inserted) {
                        log.info("Version was inserted, id={}, version={}", id, version);
                    } else {
                        log.info("Version was updated, id={}, version={}", id, version);
                    }
                });
    }

    Uni<Tuple2<Long, Boolean>> upsertQuery(SqlConnection sqlConnection, int shard, VersionModel version) {
        try {
            final var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            final var stageConfig = objectMapper.writeValueAsString(version.getStageConfig());
            final var sourceCode = objectMapper.writeValueAsString(version.getSourceCode());
            final var bytecode = objectMapper.writeValueAsString(version.getBytecode());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(Arrays.asList(
                            version.getCreated().atOffset(ZoneOffset.UTC),
                            version.getModified().atOffset(ZoneOffset.UTC),
                            version.getUuid(),
                            version.getTenant(),
                            version.getStage(),
                            stageConfig,
                            sourceCode,
                            bytecode,
                            version.getStatus(),
                            version.getErrors()
                    )))
                    .map(rowSet -> {
                        final var row = rowSet.iterator().next();
                        final var id = row.getLong("id");
                        final var inserted = row.getBoolean("inserted");
                        return Tuple2.of(id, inserted);
                    });
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
