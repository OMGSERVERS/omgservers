package com.omgservers.application.module.matchmakerModule.impl.operation.insertRequestOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.module.matchmakerModule.model.request.RequestModel;
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
class InsertRequestOperationImpl implements InsertRequestOperation {

    static private final String sql = """
            insert into $schema.tab_matchmaker_request(id, matchmaker_id, created, config)
            values($1, $2, $3, $4)
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> insertRequest(final SqlConnection sqlConnection,
                                   final int shard,
                                   final RequestModel request) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (request == null) {
            throw new ServerSideBadRequestException("request is null");
        }

        return insertQuery(sqlConnection, shard, request)
                .onFailure(PgException.class)
                .transform(t -> new ServerSideConflictException(String
                        .format("unhandled PgException, %s, matchmakerRequest=%s", t.getMessage(), request)));
    }

    Uni<Void> insertQuery(final SqlConnection sqlConnection,
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
                        add(configString);
                    }}))
                    .replaceWithVoid();
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
