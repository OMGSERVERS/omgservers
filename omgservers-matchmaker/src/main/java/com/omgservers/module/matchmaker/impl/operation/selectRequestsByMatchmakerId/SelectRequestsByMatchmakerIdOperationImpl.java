package com.omgservers.module.matchmaker.impl.operation.selectRequestsByMatchmakerId;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.request.RequestConfigModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectRequestsByMatchmakerIdOperationImpl implements SelectRequestsByMatchmakerIdOperation {

    static private final String sql = """
            select id, matchmaker_id, created, modified, user_id, client_id, mode, config
            from $schema.tab_matchmaker_request
            where matchmaker_id = $1
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<List<RequestModel>> selectRequestsByMatchmakerId(final SqlConnection sqlConnection,
                                                                final int shard,
                                                                final Long matchmakerId) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (matchmakerId == null) {
            throw new IllegalArgumentException("uuid is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(matchmakerId))
                .map(RowSet::iterator)
                .map(iterator -> {
                    final List<RequestModel> requests = new ArrayList<RequestModel>();
                    while (iterator.hasNext()) {
                        try {
                            final var request = createRequest(iterator.next());
                            requests.add(request);
                        } catch (IOException e) {
                            log.error("Request config can't be parsed, " +
                                    "matchmakerId={}, {}", matchmakerId, e.getMessage());
                        }
                    }
                    if (requests.size() > 0) {
                        log.info("Requests were selected, count={}, matchmakerId={}", requests.size(), matchmakerId);
                    } else {
                        log.info("Requests were not found, matchmakerId={}", matchmakerId);
                    }
                    return requests;
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    RequestModel createRequest(Row row) throws IOException {
        RequestModel request = new RequestModel();
        request.setId(row.getLong("id"));
        request.setMatchmakerId(row.getLong("matchmaker_id"));
        request.setCreated(row.getOffsetDateTime("created").toInstant());
        request.setModified(row.getOffsetDateTime("modified").toInstant());
        request.setUserId(row.getLong("user_id"));
        request.setClientId(row.getLong("client_id"));
        request.setMode(row.getString("mode"));
        request.setConfig(objectMapper.readValue(row.getString("config"), RequestConfigModel.class));
        return request;
    }
}
