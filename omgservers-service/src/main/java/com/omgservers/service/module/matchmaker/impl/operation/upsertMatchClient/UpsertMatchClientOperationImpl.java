package com.omgservers.service.module.matchmaker.impl.operation.upsertMatchClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.model.event.body.MatchClientCreatedEventBodyModel;
import com.omgservers.model.matchClient.MatchClientModel;
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
class UpsertMatchClientOperationImpl implements UpsertMatchClientOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertMatchClient(final ChangeContext<?> changeContext,
                                          final SqlConnection sqlConnection,
                                          final int shard,
                                          final MatchClientModel matchClient) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_matchmaker_match_client(
                            id, matchmaker_id, match_id, created, modified,
                            user_id, client_id, group_name, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        matchClient.getId(),
                        matchClient.getMatchmakerId(),
                        matchClient.getMatchId(),
                        matchClient.getCreated().atOffset(ZoneOffset.UTC),
                        matchClient.getModified().atOffset(ZoneOffset.UTC),
                        matchClient.getUserId(),
                        matchClient.getClientId(),
                        matchClient.getGroupName(),
                        getConfigString(matchClient),
                        matchClient.getDeleted()
                ),
                () -> new MatchClientCreatedEventBodyModel(
                        matchClient.getMatchmakerId(),
                        matchClient.getMatchId(),
                        matchClient.getId()),
                () -> null
        );
    }

    String getConfigString(MatchClientModel matchClient) {
        try {
            return objectMapper.writeValueAsString(matchClient.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
