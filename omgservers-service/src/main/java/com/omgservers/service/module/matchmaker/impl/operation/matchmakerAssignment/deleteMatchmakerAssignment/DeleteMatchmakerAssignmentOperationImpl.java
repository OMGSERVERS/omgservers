package com.omgservers.service.module.matchmaker.impl.operation.matchmakerAssignment.deleteMatchmakerAssignment;

import com.omgservers.model.event.body.module.matchmaker.MatchmakerAssignmentDeletedEventBodyModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerRequest.selectMatchmakerRequest.SelectMatchmakerRequestOperation;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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
class DeleteMatchmakerAssignmentOperationImpl implements DeleteMatchmakerAssignmentOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectMatchmakerRequestOperation selectMatchmakerRequestOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteMatchmakerAssignment(final ChangeContext<?> changeContext,
                                                   final SqlConnection sqlConnection,
                                                   final int shard,
                                                   final Long matchmakerId,
                                                   final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_matchmaker_assignment
                        set modified = $3, deleted = true
                        where matchmaker_id = $1 and id = $2 and deleted = false
                        """,
                List.of(
                        matchmakerId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new MatchmakerAssignmentDeletedEventBodyModel(matchmakerId, id),
                () -> null
        );
    }
}
