package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchCreatedEventBodyModel;
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
class UpsertMatchmakerMatchOperationImpl implements UpsertMatchmakerMatchOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final MatchmakerMatchModel matchmakerMatch) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_matchmaker_match(
                            id, idempotency_key, matchmaker_id, created, modified, runtime_id, config, status, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        matchmakerMatch.getId(),
                        matchmakerMatch.getIdempotencyKey(),
                        matchmakerMatch.getMatchmakerId(),
                        matchmakerMatch.getCreated().atOffset(ZoneOffset.UTC),
                        matchmakerMatch.getModified().atOffset(ZoneOffset.UTC),
                        matchmakerMatch.getRuntimeId(),
                        getConfigString(matchmakerMatch),
                        matchmakerMatch.getStatus(),
                        matchmakerMatch.getDeleted()
                ),
                () -> new MatchmakerMatchCreatedEventBodyModel(matchmakerMatch.getMatchmakerId(), matchmakerMatch.getId()),
                () -> null
        );
    }

    String getConfigString(final MatchmakerMatchModel matchmakerMatch) {
        try {
            return objectMapper.writeValueAsString(matchmakerMatch.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
