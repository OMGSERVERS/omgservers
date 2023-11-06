package com.omgservers.service.module.matchmaker.impl.operation.selectMatchmakerCommandsByMatchmakerId;

import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.service.module.matchmaker.impl.mappers.MatchmakerCommandModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
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
class SelectMatchmakerCommandsByMatchmakerIdOperationImpl
        implements SelectMatchmakerCommandsByMatchmakerIdOperation {

    final SelectListOperation selectListOperation;

    final MatchmakerCommandModelMapper matchmakerCommandModelMapper;

    @Override
    public Uni<List<MatchmakerCommandModel>> selectMatchmakerCommandsByMatchmakerIdAndMatchId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long matchmakerId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, matchmaker_id, created, modified, qualifier, body, deleted
                        from $schema.tab_matchmaker_command
                        where matchmaker_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(matchmakerId),
                "Matchmaker command",
                matchmakerCommandModelMapper::fromRow);
    }
}
