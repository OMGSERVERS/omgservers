package com.omgservers.application.module.matchmakerModule.impl.operation.upsertMatchmakerOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.base.impl.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.matchmaker.MatchmakerModel;
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
class UpsertMatchmakerOperationImpl implements UpsertMatchmakerOperation {

    static private final String sql = """
            insert into $schema.tab_matchmaker(id, created, modified, tenant_id, stage_id)
            values($1, $2, $3, $4, $5)
            on conflict (id) do
            update set modified = $3, tenant_id = $4, stage_id = $5
            returning xmax::text::int = 0 as inserted
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertMatchmaker(final SqlConnection sqlConnection,
                                         final int shard,
                                         final MatchmakerModel matchmaker) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (matchmaker == null) {
            throw new ServerSideBadRequestException("matchmaker is null");
        }

        return upsertQuery(sqlConnection, shard, matchmaker)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Matchmaker was created, shard={}, matchmaker={}", shard, matchmaker);
                    } else {
                        log.info("Matchmaker was updated, shard={}, matchmaker={}", shard, matchmaker);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> new ServerSideConflictException(String
                        .format("unhandled PgException, %s, matchmaker=%s", t.getMessage(), matchmaker)));
    }

    Uni<Boolean> upsertQuery(final SqlConnection sqlConnection,
                             final int shard,
                             final MatchmakerModel matchmaker) {
        var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(new ArrayList<>() {{
                    add(matchmaker.getId());
                    add(matchmaker.getCreated().atOffset(ZoneOffset.UTC));
                    add(matchmaker.getModified().atOffset(ZoneOffset.UTC));
                    add(matchmaker.getTenantId());
                    add(matchmaker.getStageId());
                }}))
                .map(rowSet -> rowSet.iterator().next().getBoolean("inserted"));
    }
}
