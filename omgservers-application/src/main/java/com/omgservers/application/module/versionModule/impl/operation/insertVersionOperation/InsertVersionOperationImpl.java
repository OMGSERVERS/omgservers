package com.omgservers.application.module.versionModule.impl.operation.insertVersionOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.versionModule.model.VersionModel;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideInternalException;
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
class InsertVersionOperationImpl implements InsertVersionOperation {

    static private final String sql = """
            insert into $schema.tab_version(id, created, modified, tenant_id, stage_id, stage_config, source_code, bytecode, status, errors)
            values($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> insertVersion(final SqlConnection sqlConnection,
                                   final int shard,
                                   final VersionModel version) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (version == null) {
            throw new ServerSideBadRequestException("version is null");
        }

        return insertQuery(sqlConnection, shard, version)
                .invoke(voidItem -> log.info("Version was inserted, version={}", version));
    }

    Uni<Void> insertQuery(SqlConnection sqlConnection, int shard, VersionModel version) {
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
                    .replaceWithVoid();
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
