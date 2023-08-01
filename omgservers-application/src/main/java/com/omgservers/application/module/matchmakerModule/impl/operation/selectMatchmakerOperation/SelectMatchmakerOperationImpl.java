package com.omgservers.application.module.matchmakerModule.impl.operation.selectMatchmakerOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.module.matchmakerModule.model.matchmaker.MatchmakerModel;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchmakerOperationImpl implements SelectMatchmakerOperation {

    static private final String sql = """
            select id, created, modified, tenant_id, stage_id
            from $schema.tab_matchmaker
            where id = $1
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<MatchmakerModel> selectMatchmaker(final SqlConnection sqlConnection,
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
                        final var matchmaker = createMatchmaker(iterator.next());
                        log.info("Matchmaker was found, matchmaker={}", matchmaker);
                        return matchmaker;
                    } else {
                        throw new ServerSideNotFoundException("matchmaker was not found, id=" + id);
                    }
                });
    }

    MatchmakerModel createMatchmaker(Row row) {
        MatchmakerModel matchmakerModel = new MatchmakerModel();
        matchmakerModel.setId(row.getLong("id"));
        matchmakerModel.setCreated(row.getOffsetDateTime("created").toInstant());
        matchmakerModel.setModified(row.getOffsetDateTime("modified").toInstant());
        matchmakerModel.setTenantId(row.getLong("tenant_id"));
        matchmakerModel.setStageId(row.getLong("stage_id"));
        return matchmakerModel;
    }
}
