package com.omgservers.module.matchmaker.impl.operation.upsertMatchmakerCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
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
class UpsertMatchmakerCommandOperationImpl implements UpsertMatchmakerCommandOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertMatchmakerCommand(final ChangeContext<?> changeContext,
                                                final SqlConnection sqlConnection,
                                                final int shard,
                                                final MatchmakerCommandModel matchmakerCommand) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_matchmaker_command(
                            id, matchmaker_id, created, modified, qualifier, body)
                        values($1, $2, $3, $4, $5, $6)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        matchmakerCommand.getId(),
                        matchmakerCommand.getMatchmakerId(),
                        matchmakerCommand.getCreated().atOffset(ZoneOffset.UTC),
                        matchmakerCommand.getModified().atOffset(ZoneOffset.UTC),
                        matchmakerCommand.getQualifier(),
                        getBodyString(matchmakerCommand)
                ),
                () -> null,
                () -> logModelFactory.create("Matchmaker command was inserted, matchmakerCommand=" + matchmakerCommand)
        );
    }

    String getBodyString(MatchmakerCommandModel matchmakerCommand) {
        try {
            return objectMapper.writeValueAsString(matchmakerCommand.getBody());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
