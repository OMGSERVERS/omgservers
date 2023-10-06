package com.omgservers.module.matchmaker.impl.operation.selectMatchmakerCommandsByMatchmakerIdAndStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandStatusEnum;
import com.omgservers.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchmakerCommandsByMatchmakerIdAndStatusOperationImpl
        implements SelectMatchmakerCommandsByMatchmakerIdAndStatusOperation {

    final SelectListOperation selectListOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<List<MatchmakerCommandModel>> selectMatchmakerCommandsByMatchmakerIdAndStatus(
            final SqlConnection sqlConnection,
            final int shard,
            final Long matchmakerId,
            final MatchmakerCommandStatusEnum status) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, matchmaker_id, created, modified, qualifier, body, status
                        from $schema.tab_matchmaker_command
                        where matchmaker_id = $1 and status = $2
                        order by id asc
                        """,
                Arrays.asList(matchmakerId, status),
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
        matchmakerCommand.setStatus(MatchmakerCommandStatusEnum.valueOf(row.getString("status")));
        return matchmakerCommand;
    }
}
