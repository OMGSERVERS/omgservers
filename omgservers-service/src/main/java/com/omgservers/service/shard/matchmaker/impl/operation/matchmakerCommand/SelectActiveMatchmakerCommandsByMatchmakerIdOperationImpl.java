package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerCommand;

import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.service.shard.matchmaker.impl.mappers.MatchmakerCommandModelMapper;
import com.omgservers.service.operation.server.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveMatchmakerCommandsByMatchmakerIdOperationImpl
        implements SelectActiveMatchmakerCommandsByMatchmakerIdOperation {

    final SelectListOperation selectListOperation;

    final MatchmakerCommandModelMapper matchmakerCommandModelMapper;

    @Override
    public Uni<List<MatchmakerCommandModel>> execute(
            final SqlConnection sqlConnection,
            final int slot,
            final Long matchmakerId) {
        return selectListOperation.selectList(
                sqlConnection,
                slot,
                """
                        select id, idempotency_key, matchmaker_id, created, modified, qualifier, body, deleted
                        from $slot.tab_matchmaker_command
                        where matchmaker_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(matchmakerId),
                "Matchmaker command",
                matchmakerCommandModelMapper::execute);
    }
}
