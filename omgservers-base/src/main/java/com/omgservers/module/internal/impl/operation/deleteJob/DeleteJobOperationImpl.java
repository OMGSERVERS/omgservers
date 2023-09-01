package com.omgservers.module.internal.impl.operation.deleteJob;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteJobOperationImpl implements DeleteJobOperation {

    static private final String sql = """
            delete from internal.tab_job where shard_key = $1 and entity = $2
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;

    @Override
    public Uni<Boolean> deleteJob(SqlConnection sqlConnection, Long shardKey, Long entity) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (shardKey == null) {
            throw new ServerSideBadRequestException("shardKey is null");
        }
        if (entity == null) {
            throw new ServerSideBadRequestException("entity is null");
        }

        return sqlConnection.preparedQuery(sql)
                .execute(Tuple.of(shardKey, entity))
                .map(rowSet -> rowSet.rowCount() > 0)
                .invoke(deleted -> {
                    if (deleted) {
                        log.info("Job was deleted, shardKey={}, entity={}", shardKey, entity);
                    } else {
                        log.warn("Job was not found, skip operation, shardKey={}, entity={}", shardKey, entity);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }
}
