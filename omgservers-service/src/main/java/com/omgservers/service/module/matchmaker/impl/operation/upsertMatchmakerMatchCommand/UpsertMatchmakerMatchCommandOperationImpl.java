package com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmakerMatchCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.matchCommand.MatchmakerMatchCommandModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertMatchmakerMatchCommandOperationImpl implements UpsertMatchmakerMatchCommandOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertMatchmakerMatchCommand(final ChangeContext<?> changeContext,
                                                     final SqlConnection sqlConnection,
                                                     final int shard,
                                                     final MatchmakerMatchCommandModel matchmakerMatchCommand) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_matchmaker_match_command(
                            id, matchmaker_id, match_id, created, modified, qualifier, body, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        matchmakerMatchCommand.getId(),
                        matchmakerMatchCommand.getMatchmakerId(),
                        matchmakerMatchCommand.getMatchId(),
                        matchmakerMatchCommand.getCreated().atOffset(ZoneOffset.UTC),
                        matchmakerMatchCommand.getModified().atOffset(ZoneOffset.UTC),
                        matchmakerMatchCommand.getQualifier(),
                        getBodyString(matchmakerMatchCommand),
                        matchmakerMatchCommand.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }

    String getBodyString(MatchmakerMatchCommandModel matchCommand) {
        try {
            return objectMapper.writeValueAsString(matchCommand.getBody());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.OBJECT_WRONG, e.getMessage(), e);
        }
    }
}
