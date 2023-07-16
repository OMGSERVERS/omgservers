package com.omgservers.application.module.matchmakerModule.impl.operation.insertMatchOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.module.matchmakerModule.model.match.MatchModel;
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
class InsertMatchOperationImpl implements InsertMatchOperation {

    static private final String sql = """
            insert into $schema.tab_matchmaker_match(matchmaker_uuid, created, modified, uuid, runtime config)
            values($1, $2, $3, $4, $5, $6)
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> insertMatch(final SqlConnection sqlConnection,
                                 final int shard,
                                 final MatchModel match) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (match == null) {
            throw new ServerSideBadRequestException("match is null");
        }

        return insertQuery(sqlConnection, shard, match)
                .onFailure(PgException.class)
                .transform(t -> new ServerSideConflictException(String
                        .format("unhandled PgException, %s, match=%s", t.getMessage(), match)));
    }

    Uni<Void> insertQuery(final SqlConnection sqlConnection,
                          final int shard,
                          final MatchModel match) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            var configString = objectMapper.writeValueAsString(match.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(new ArrayList<>() {{
                        add(match.getMatchmaker());
                        add(match.getCreated().atOffset(ZoneOffset.UTC));
                        add(match.getModified().atOffset(ZoneOffset.UTC));
                        add(match.getUuid());
                        add(match.getRuntime());
                        add(configString);
                    }}))
                    .replaceWithVoid();
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
