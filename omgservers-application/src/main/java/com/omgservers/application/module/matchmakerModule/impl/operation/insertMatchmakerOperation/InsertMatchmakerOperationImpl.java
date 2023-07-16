package com.omgservers.application.module.matchmakerModule.impl.operation.insertMatchmakerOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.module.matchmakerModule.model.matchmaker.MatchmakerModel;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InsertMatchmakerOperationImpl implements InsertMatchmakerOperation {

    static private final String sql = """
            insert into $schema.tab_matchmaker(created, uuid, tenant, stage)
            values($1, $2, $3, $4)
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> insertMatchmaker(final SqlConnection sqlConnection,
                                      final int shard,
                                      final MatchmakerModel matchmaker) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (matchmaker == null) {
            throw new ServerSideBadRequestException("matchmaker is null");
        }

        return insertQuery(sqlConnection, shard, matchmaker)
                .invoke(voidItem -> log.info("Matchmaker was inserted, matchmaker={}", matchmaker))
                .onFailure(PgException.class)
                .transform(t -> new ServerSideConflictException(String
                        .format("unhandled PgException, %s, matchmaker=%s", t.getMessage(), matchmaker)));
    }

    Uni<Void> insertQuery(final SqlConnection sqlConnection,
                          final int shard,
                          final MatchmakerModel matchmaker) {
        var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(new ArrayList<>() {{
                    add(matchmaker.getCreated().atOffset(ZoneOffset.UTC));
                    add(matchmaker.getUuid());
                    add(matchmaker.getTenant());
                    add(matchmaker.getStage());
                }}))
                .replaceWithVoid();
    }
}
