package com.omgservers.module.runtime.impl.operation.upsertRuntimeCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertRuntimeCommandOperationImpl implements UpsertRuntimeCommandOperation {

    static private final String sql = """
            insert into $schema.tab_runtime_command(id, runtime_id, created, modified, qualifier, body, status, step)
            values($1, $2, $3, $4, $5, $6, $7, $8)
            on conflict (id) do
            nothing
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertRuntimeCommand(final SqlConnection sqlConnection,
                                             final int shard,
                                             final RuntimeCommandModel runtimeCommand) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (runtimeCommand == null) {
            throw new ServerSideBadRequestException("runtimeCommand is null");
        }

        return upsertQuery(sqlConnection, shard, runtimeCommand)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Runtime command was inserted, shard={}, runtimeCommand={}", shard, runtimeCommand);
                    } else {
                        log.info("Runtime command was updated, shard={}, runtimeCommand={}", shard, runtimeCommand);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertQuery(final SqlConnection sqlConnection,
                             final int shard,
                             final RuntimeCommandModel runtimeCommand) {
        try {
            var bodyString = objectMapper.writeValueAsString(runtimeCommand.getBody());
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(new ArrayList<>() {{
                        add(runtimeCommand.getId());
                        add(runtimeCommand.getRuntimeId());
                        add(runtimeCommand.getCreated().atOffset(ZoneOffset.UTC));
                        add(runtimeCommand.getModified().atOffset(ZoneOffset.UTC));
                        add(runtimeCommand.getQualifier());
                        add(bodyString);
                        add(runtimeCommand.getStatus());
                        add(runtimeCommand.getStep());
                    }}))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
