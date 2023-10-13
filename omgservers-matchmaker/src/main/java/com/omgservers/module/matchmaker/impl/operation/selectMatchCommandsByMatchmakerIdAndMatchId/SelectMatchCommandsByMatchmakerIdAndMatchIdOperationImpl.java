package com.omgservers.module.matchmaker.impl.operation.selectMatchCommandsByMatchmakerIdAndMatchId;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.model.matchCommand.MatchCommandQualifierEnum;
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
class SelectMatchCommandsByMatchmakerIdAndMatchIdOperationImpl
        implements SelectMatchCommandsByMatchmakerIdAndMatchIdOperation {

    final SelectListOperation selectListOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<List<MatchCommandModel>> selectMatchCommandsByMatchmakerIdAndMatchId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long matchmakerId,
            final Long matchId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, matchmaker_id, match_id, created, modified, qualifier, body
                        from $schema.tab_matchmaker_match_command
                        where matchmaker_id = $1 and match_id = $2
                        order by id asc
                        """,
                Arrays.asList(matchmakerId, matchId),
                "Match command",
                this::createMatchCommand);
    }

    MatchCommandModel createMatchCommand(Row row) {
        final var matchCommand = new MatchCommandModel();
        matchCommand.setId(row.getLong("id"));
        matchCommand.setMatchmakerId(row.getLong("matchmaker_id"));
        matchCommand.setMatchId(row.getLong("match_id"));
        matchCommand.setCreated(row.getOffsetDateTime("created").toInstant());
        matchCommand.setModified(row.getOffsetDateTime("modified").toInstant());
        final var qualifier = MatchCommandQualifierEnum.valueOf(row.getString("qualifier"));
        matchCommand.setQualifier(qualifier);
        try {
            final var body = objectMapper.readValue(row.getString("body"), qualifier.getBodyClass());
            matchCommand.setBody(body);
        } catch (IOException e) {
            throw new ServerSideConflictException("match command can't be parsed, " +
                    "matchCommand=" + matchCommand, e);
        }
        return matchCommand;
    }
}
