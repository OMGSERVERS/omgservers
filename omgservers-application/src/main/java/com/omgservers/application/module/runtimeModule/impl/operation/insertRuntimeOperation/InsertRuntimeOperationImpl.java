package com.omgservers.application.module.runtimeModule.impl.operation.insertRuntimeOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.module.runtimeModule.model.RuntimeModel;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
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
class InsertRuntimeOperationImpl implements InsertRuntimeOperation {

    static private final String sql = """
            insert into $schema.tab_runtime(id, created, matchmaker_id, match_id, config)
            values($1, $2, $3, $4, $5)
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> insertRuntime(final SqlConnection sqlConnection,
                                   final int shard,
                                   final RuntimeModel runtime) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (runtime == null) {
            throw new ServerSideBadRequestException("runtime is null");
        }

        return insertQuery(sqlConnection, shard, runtime)
                .invoke(voidItem -> log.info("Runtime was inserted, {}", runtime))
                .onFailure(PgException.class)
                .transform(t -> new ServerSideConflictException(String
                        .format("unhandled PgException, %s, runtime=%s", t.getMessage(), runtime)));
    }

    Uni<Void> insertQuery(final SqlConnection sqlConnection,
                          final int shard,
                          final RuntimeModel runtime) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            var configString = objectMapper.writeValueAsString(runtime.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(new ArrayList<>() {{
                        add(runtime.getId());
                        add(runtime.getCreated().atOffset(ZoneOffset.UTC));
                        add(runtime.getMatchmakerId());
                        add(runtime.getMatchId());
                        add(configString);
                    }}))
                    .replaceWithVoid();
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
