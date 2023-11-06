package com.omgservers.service.module.matchmaker.impl.operation.selectMatchClient;

import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchClientModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
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

    final MatchClientModelMapper matchClientModelMapper;

    @Override
    public Uni<MatchClientModel> selectMatchClient(final SqlConnection sqlConnection,
                                                   final int shard,
                                                   final Long matchmakerId,
                                                   final Long id,
                                                   final Boolean deleted) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, matchmaker_id, match_id, created, modified,
                            user_id, client_id, group_name, config, deleted
                        from $schema.tab_matchmaker_match_client
                        where matchmaker_id = $1 and id = $2 and deleted = $3
                        limit 1
                        """,
                Arrays.asList(
                        matchmakerId,
                        id,
                        deleted),
                "Match client",
                matchClientModelMapper::fromRow);
    }
}
