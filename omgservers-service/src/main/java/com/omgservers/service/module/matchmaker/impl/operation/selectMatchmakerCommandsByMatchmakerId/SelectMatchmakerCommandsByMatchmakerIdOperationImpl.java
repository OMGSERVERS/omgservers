package com.omgservers.service.module.matchmaker.impl.operation.selectMatchmakerCommandsByMatchmakerId;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchmakerCommandsByMatchmakerIdOperationImpl
        implements SelectMatchmakerCommandsByMatchmakerIdOperation {

    final SelectListOperation selectListOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<List<MatchmakerCommandModel>> selectMatchmakerCommandsByMatchmakerIdAndMatchId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long matchmakerId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, matchmaker_id, created, modified, qualifier, body
                        from $schema.tab_matchmaker_command
                        where matchmaker_id = $1
                        order by id asc
                        """,
                Collections.singletonList(matchmakerId),
                "Matchmaker command",
                this::createMatchmakerCommand);
    }

    MatchmakerCommandModel createMatchmakerCommand(Row row) {
        final var matchmakerCommand = new MatchmakerCommandModel();
        matchmakerCommand.setId(row.getLong("id"));
        matchmakerCommand.setMatchmakerId(row.getLong("matchmaker_id"));
        matchmakerCommand.setCreated(row.getOffsetDateTime("created").toInstant());
        matchmakerCommand.setModified(row.getOffsetDateTime("modified").toInstant());
        final var qualifier = MatchmakerCommandQualifierEnum.valueOf(row.getString("qualifier"));
        matchmakerCommand.setQualifier(qualifier);
        try {
            final var body = objectMapper.readValue(row.getString("body"), qualifier.getBodyClass());
            matchmakerCommand.setBody(body);
        } catch (IOException e) {
            throw new ServerSideConflictException("matchmaker command can't be parsed, " +
                    "matchmakerCommand=" + matchmakerCommand, e);
        }
        return matchmakerCommand;
    }
}
