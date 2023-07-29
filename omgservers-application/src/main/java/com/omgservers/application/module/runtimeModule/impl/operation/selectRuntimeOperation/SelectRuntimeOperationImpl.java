package com.omgservers.application.module.runtimeModule.impl.operation.selectRuntimeOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.module.runtimeModule.model.RuntimeConfigModel;
import com.omgservers.application.module.runtimeModule.model.RuntimeModel;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectRuntimeOperationImpl implements SelectRuntimeOperation {

    static private final String sql = """
            select id, created, modified, matchmaker_id, match_id, config
            from $schema.tab_runtime
            where id = $1
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<RuntimeModel> selectRuntime(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long id) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(id))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        try {
                            final var match = createRuntime(iterator.next());
                            log.info("Runtime was found, runtime={}", match);
                            return match;
                        } catch (IOException e) {
                            throw new ServerSideConflictException("runtime can't be parsed, id=" + id, e);
                        }
                    } else {
                        throw new ServerSideNotFoundException("runtime was not found, id=" + id);
                    }
                });
    }

    RuntimeModel createRuntime(Row row) throws IOException {
        RuntimeModel runtime = new RuntimeModel();
        runtime.setId(row.getLong("id"));
        runtime.setCreated(row.getOffsetDateTime("created").toInstant());
        runtime.setModified(row.getOffsetDateTime("modified").toInstant());
        runtime.setMatchmakerId(row.getLong("matchmaker_id"));
        runtime.setMatchId(row.getLong("match_id"));
        runtime.setConfig(objectMapper.readValue(row.getString("config"), RuntimeConfigModel.class));
        return runtime;
    }
}
