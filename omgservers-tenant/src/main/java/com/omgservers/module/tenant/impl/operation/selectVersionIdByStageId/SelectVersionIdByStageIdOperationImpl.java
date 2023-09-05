package com.omgservers.module.tenant.impl.operation.selectVersionIdByStageId;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectVersionIdByStageIdOperationImpl implements SelectVersionIdByStageIdOperation {

    static private final String SQL = """
            select id
            from $schema.tab_tenant_version
            where stage_id = $1
            order by id desc
            limit 1
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Long> selectVersionIdByStageId(final SqlConnection sqlConnection,
                                              final int shard,
                                              final Long stageId) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (stageId == null) {
            throw new ServerSideBadRequestException("stageId is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(SQL, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(stageId))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        final var row = iterator.next();
                        final var versionId = row.getLong("id");
                        log.info("VersionId was selected by stageId, stageId={}, versionId={}", stageId, versionId);
                        return versionId;
                    } else {
                        throw new ServerSideNotFoundException(String.format("VersionId was not found, " +
                                "stageId=%s", stageId));
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }
}
