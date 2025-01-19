package com.omgservers.service.module.matchmaker.impl.operation.matchmakerRequest;

import com.omgservers.service.event.body.module.matchmaker.MatchmakerRequestDeletedEventBodyModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteMatchmakerRequestOperationImpl implements DeleteMatchmakerRequestOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectMatchmakerRequestOperation selectMatchmakerRequestOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final Long matchmakerId,
                                final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_matchmaker_request
                        set modified = $3, deleted = true
                        where matchmaker_id = $1 and id = $2 and deleted = false
                        """,
                List.of(
                        matchmakerId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new MatchmakerRequestDeletedEventBodyModel(matchmakerId, id),
                () -> null
        );
    }
}
