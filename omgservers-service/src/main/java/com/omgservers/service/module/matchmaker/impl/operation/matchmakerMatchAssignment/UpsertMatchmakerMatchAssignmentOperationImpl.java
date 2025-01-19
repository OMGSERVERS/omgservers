package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchAssignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchAssignmentCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertMatchmakerMatchAssignmentOperationImpl implements UpsertMatchmakerMatchAssignmentOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final MatchmakerMatchAssignmentModel matchmakerMatchAssignment) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_matchmaker_match_assignment(
                            id, idempotency_key, matchmaker_id, match_id, created, modified, user_id, client_id, 
                            group_name, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        matchmakerMatchAssignment.getId(),
                        matchmakerMatchAssignment.getIdempotencyKey(),
                        matchmakerMatchAssignment.getMatchmakerId(),
                        matchmakerMatchAssignment.getMatchId(),
                        matchmakerMatchAssignment.getCreated().atOffset(ZoneOffset.UTC),
                        matchmakerMatchAssignment.getModified().atOffset(ZoneOffset.UTC),
                        matchmakerMatchAssignment.getUserId(),
                        matchmakerMatchAssignment.getClientId(),
                        matchmakerMatchAssignment.getGroupName(),
                        getConfigString(matchmakerMatchAssignment),
                        matchmakerMatchAssignment.getDeleted()
                ),
                () -> new MatchmakerMatchAssignmentCreatedEventBodyModel(
                        matchmakerMatchAssignment.getMatchmakerId(),
                        matchmakerMatchAssignment.getMatchId(),
                        matchmakerMatchAssignment.getId()),
                () -> null
        );
    }

    String getConfigString(final MatchmakerMatchAssignmentModel matchmakerMatchAssignment) {
        try {
            return objectMapper.writeValueAsString(matchmakerMatchAssignment.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
