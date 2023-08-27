package com.omgservers.module.matchmaker.impl.operation.selectMatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.match.MatchConfigModel;
import com.omgservers.model.match.MatchModel;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchOperationImpl implements SelectMatchOperation {

    static private final String sql = """
            select id, matchmaker_id, created, modified, runtime_id, config
            from $schema.tab_matchmaker_match
            where id = $1
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<MatchModel> selectMatch(final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long id) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (id == null) {
            throw new IllegalArgumentException("uuid is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(id))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        try {
                            final var match = createMatch(iterator.next());
                            log.info("Match was found, match={}", match);
                            return match;
                        } catch (IOException e) {
                            throw new ServerSideConflictException("match can't be parsed, id=" + id, e);
                        }
                    } else {
                        throw new ServerSideNotFoundException("match was not found, id=" + id);
                    }
                });
    }

    MatchModel createMatch(Row row) throws IOException {
        MatchModel match = new MatchModel();
        match.setId(row.getLong("id"));
        match.setMatchmakerId(row.getLong("matchmaker_id"));
        match.setCreated(row.getOffsetDateTime("created").toInstant());
        match.setModified(row.getOffsetDateTime("modified").toInstant());
        match.setRuntimeId(row.getLong("runtime_id"));
        match.setConfig(objectMapper.readValue(row.getString("config"), MatchConfigModel.class));
        return match;
    }
}
