package com.omgservers.application.module.matchmakerModule.impl.operation.selectMatchOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.module.matchmakerModule.model.match.MatchConfigModel;
import com.omgservers.application.module.matchmakerModule.model.match.MatchModel;
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
class SelectMatchOperationImpl implements SelectMatchOperation {

    static private final String sql = """
            select matchmaker_uuid, created, modified, uuid, runtime, config
            from $schema.tab_matchmaker_match
            where uuid = $1
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<MatchModel> selectMatch(final SqlConnection sqlConnection,
                                       final int shard,
                                       final UUID uuid) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (uuid == null) {
            throw new IllegalArgumentException("uuid is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(uuid))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        try {
                            final var match = createMatch(iterator.next());
                            log.info("Match was found, match={}", match);
                            return match;
                        } catch (IOException e) {
                            throw new ServerSideConflictException("match can't be parsed, uuid=" + uuid, e);
                        }
                    } else {
                        throw new ServerSideNotFoundException("match was not found, uuid=" + uuid);
                    }
                });
    }

    MatchModel createMatch(Row row) throws IOException {
        MatchModel match = new MatchModel();
        match.setMatchmaker(row.getUUID("matchmaker_uuid"));
        match.setCreated(row.getOffsetDateTime("created").toInstant());
        match.setModified(row.getOffsetDateTime("modified").toInstant());
        match.setUuid(row.getUUID("uuid"));
        match.setRuntime(row.getUUID("runtime"));
        match.setConfig(objectMapper.readValue(row.getString("config"), MatchConfigModel.class));
        return match;
    }
}
