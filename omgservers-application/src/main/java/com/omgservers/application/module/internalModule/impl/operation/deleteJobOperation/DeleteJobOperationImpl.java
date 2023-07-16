package com.omgservers.application.module.internalModule.impl.operation.deleteJobOperation;

import com.omgservers.application.exception.ServerSideBadRequestException;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
class DeleteJobOperationImpl implements DeleteJobOperation {

    static private final String sql = """
            delete from internal.tab_job where shard_key = $1 and entity = $2
            """;

    @Override
    public Uni<Boolean> deleteJob(SqlConnection sqlConnection, UUID shardKey, UUID entity) {
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
                });
    }
}
