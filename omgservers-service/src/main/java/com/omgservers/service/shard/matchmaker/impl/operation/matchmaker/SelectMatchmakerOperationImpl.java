package com.omgservers.service.shard.matchmaker.impl.operation.matchmaker;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.service.shard.matchmaker.impl.mappers.MatchmakerModelMapper;
import com.omgservers.service.operation.server.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchmakerOperationImpl implements SelectMatchmakerOperation {

    final SelectObjectOperation selectObjectOperation;

    final MatchmakerModelMapper matchmakerModelMapper;

    @Override
    public Uni<MatchmakerModel> execute(final SqlConnection sqlConnection,
                                        final int shard,
                                        final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, created, modified, deployment_id, deleted
                        from $shard.tab_matchmaker
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "Matchmaker",
                matchmakerModelMapper::execute);
    }
}
