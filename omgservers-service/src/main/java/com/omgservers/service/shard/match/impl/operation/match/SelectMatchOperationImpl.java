package com.omgservers.service.shard.match.impl.operation.match;

import com.omgservers.schema.model.match.MatchModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
import com.omgservers.service.shard.match.impl.mappers.MatchModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchOperationImpl implements SelectMatchOperation {

    final SelectObjectOperation selectObjectOperation;

    final MatchModelMapper matchModelMapper;

    @Override
    public Uni<MatchModel> execute(final SqlConnection sqlConnection,
                                   final int slot,
                                   final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, created, modified, matchmaker_id, runtime_id, config, deleted
                        from $slot.tab_match
                        where id = $1
                        limit 1
                        """,
                List.of(id),
                "Match",
                matchModelMapper::execute);
    }
}
