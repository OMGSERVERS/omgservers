package com.omgservers.module.matchmaker.impl.operation.upsertMatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.event.body.MatchCreatedEventBodyModel;
import com.omgservers.model.match.MatchModel;
import com.omgservers.factory.LogModelFactory;
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
class UpsertMatchOperationImpl implements UpsertMatchOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertMatch(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final int shard,
                                    final MatchModel match) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_matchmaker_match(id, matchmaker_id, created, modified, runtime_id, stopped, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        match.getId(),
                        match.getMatchmakerId(),
                        match.getCreated().atOffset(ZoneOffset.UTC),
                        match.getModified().atOffset(ZoneOffset.UTC),
                        match.getRuntimeId(),
                        match.getStopped(),
                        getConfigString(match),
                        match.getDeleted()
                ),
                () -> new MatchCreatedEventBodyModel(match.getMatchmakerId(), match.getId()),
                () -> logModelFactory.create("Match was inserted, match=" + match)
        );
    }

    String getConfigString(MatchModel match) {
        try {
            return objectMapper.writeValueAsString(match.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
