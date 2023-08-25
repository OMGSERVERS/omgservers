package com.omgservers.application.module.versionModule.impl.operation.selectStageConfigOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.base.impl.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.version.VersionStageConfigModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectStageConfigOperationImpl implements SelectStageConfigOperation {

    static private final String sql = """
            select stage_config
            from $schema.tab_version
            where id = $1
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<VersionStageConfigModel> selectStageConfig(final SqlConnection sqlConnection,
                                                          final int shard,
                                                          final Long versionId) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (versionId == null) {
            throw new ServerSideBadRequestException("id is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(versionId))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        try {
                            log.info("Version was found, versionId={}", versionId);
                            final var row = iterator.next();
                            final var stageConfig = objectMapper.readValue(row.getString("stage_config"),
                                    VersionStageConfigModel.class);
                            return stageConfig;
                        } catch (IOException e) {
                            throw new ServerSideInternalException("stage config can't be parsed, " +
                                    "versionId=" + versionId);
                        }
                    } else {
                        throw new ServerSideNotFoundException(String.format("version was not found, " +
                                "versionId=%s", versionId));
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> new ServerSideConflictException(String
                        .format("unhandled PgException, %s", t.getMessage())));
    }
}
