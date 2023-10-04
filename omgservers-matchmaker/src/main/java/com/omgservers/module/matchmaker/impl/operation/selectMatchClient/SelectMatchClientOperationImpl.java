package com.omgservers.module.matchmaker.impl.operation.selectMatchClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchClientOperationImpl implements SelectMatchClientOperation {

    final SelectObjectOperation selectObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<MatchClientModel> selectMatchClient(final SqlConnection sqlConnection,
                                                   final int shard,
                                                   final Long matchmakerId,
                                                   final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, matchmaker_id, match_id, created, modified, user_id, client_id
                        from $schema.tab_matchmaker_match_client
                        where matchmaker_id = $1 and id = $2
                        limit 1
                        """,
                Arrays.asList(matchmakerId, id),
                "Match client",
                this::createMatchClient);
    }

    MatchClientModel createMatchClient(Row row) {
        MatchClientModel matchClient = new MatchClientModel();
        matchClient.setId(row.getLong("id"));
        matchClient.setMatchmakerId(row.getLong("matchmaker_id"));
        matchClient.setMatchId(row.getLong("match_id"));
        matchClient.setCreated(row.getOffsetDateTime("created").toInstant());
        matchClient.setModified(row.getOffsetDateTime("modified").toInstant());
        matchClient.setUserId(row.getLong("user_id"));
        matchClient.setClientId(row.getLong("client_id"));
        return matchClient;
    }
}
