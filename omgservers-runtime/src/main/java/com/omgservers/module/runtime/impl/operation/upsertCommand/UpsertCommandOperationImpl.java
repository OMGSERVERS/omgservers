package com.omgservers.module.runtime.impl.operation.upsertCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
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
class UpsertCommandOperationImpl implements UpsertCommandOperation {

    static private final String sql = """
            insert into $schema.tab_runtime_command(id, runtime_id, created, modified, qualifier, body, status)
            values($1, $2, $3, $4, $5, $6, $7)
            on conflict (id) do
            update set modified = $4, qualifier = $5, body = $6, status = $7
            returning xmax::text::int = 0 as inserted
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertCommand(final SqlConnection sqlConnection,
                                      final int shard,
                                      final RuntimeCommandModel command) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (command == null) {
            throw new ServerSideBadRequestException("command is null");
        }

        return upsertQuery(sqlConnection, shard, command)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Command was inserted, shard={}, command={}", shard, command);
                    } else {
                        log.info("Command was updated, shard={}, command={}", shard, command);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> {
                    final var pgException = (PgException) t;
                    final var code = pgException.getSqlState();
                    if (code.equals("23503")) {
                        // foreign_key_violation
                        return new ServerSideNotFoundException("runtime was not found, command=" + command);
                    } else {
                        return new ServerSideConflictException(String.format("unhandled PgException, " +
                                "%s, command=%s", t.getMessage(), command));
                    }
                });
    }

    Uni<Boolean> upsertQuery(final SqlConnection sqlConnection,
                             final int shard,
                             final RuntimeCommandModel command) {
        try {
            var bodyString = objectMapper.writeValueAsString(command.getBody());
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(new ArrayList<>() {{
                        add(command.getId());
                        add(command.getRuntimeId());
                        add(command.getCreated().atOffset(ZoneOffset.UTC));
                        add(command.getModified().atOffset(ZoneOffset.UTC));
                        add(command.getQualifier());
                        add(bodyString);
                        add(command.getStatus());
                    }}))
                    .map(rowSet -> rowSet.iterator().next().getBoolean("inserted"));
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
