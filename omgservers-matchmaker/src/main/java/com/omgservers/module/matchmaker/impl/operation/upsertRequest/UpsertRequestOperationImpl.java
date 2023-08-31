package com.omgservers.module.matchmaker.impl.operation.upsertRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.request.RequestModel;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
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
class UpsertRequestOperationImpl implements UpsertRequestOperation {

    static private final String sql = """
            insert into $schema.tab_matchmaker_request(id, matchmaker_id, created, modified, user_id, client_id, mode, config)
            values($1, $2, $3, $4, $5, $6, $7, $8)
            on conflict (id) do
            nothing
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertRequest(final SqlConnection sqlConnection,
                                      final int shard,
                                      final RequestModel request) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (request == null) {
            throw new ServerSideBadRequestException("request is null");
        }

        return upsertQuery(sqlConnection, shard, request)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Request was inserted, shard={}, request={}", shard, request);
                    } else {
                        log.info("Request was updated, shard={}, request={}", shard, request);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> new ServerSideConflictException(String
                        .format("unhandled PgException, %s, matchmakerRequest=%s", t.getMessage(), request)));
    }

    Uni<Boolean> upsertQuery(final SqlConnection sqlConnection,
                             final int shard,
                             final RequestModel request) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            var configString = objectMapper.writeValueAsString(request.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(new ArrayList<>() {{
                        add(request.getId());
                        add(request.getMatchmakerId());
                        add(request.getCreated().atOffset(ZoneOffset.UTC));
                        add(request.getModified().atOffset(ZoneOffset.UTC));
                        add(request.getUserId());
                        add(request.getClientId());
                        add(request.getMode());
                        add(configString);
                    }}))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
