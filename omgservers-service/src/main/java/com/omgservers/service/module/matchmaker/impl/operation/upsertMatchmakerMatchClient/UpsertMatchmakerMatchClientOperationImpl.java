package com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmakerMatchClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.module.matchmaker.MatchmakerMatchClientCreatedEventBodyModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
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
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertMatchmakerMatchClientOperationImpl implements UpsertMatchmakerMatchClientOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertMatchmakerMatchClient(final ChangeContext<?> changeContext,
                                                    final SqlConnection sqlConnection,
                                                    final int shard,
                                                    final MatchmakerMatchClientModel matchmakerMatchClient) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_matchmaker_match_client(
                            id, idempotency_key, matchmaker_id, match_id, created, modified, user_id, client_id, 
                            group_name, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        matchmakerMatchClient.getId(),
                        matchmakerMatchClient.getIdempotencyKey(),
                        matchmakerMatchClient.getMatchmakerId(),
                        matchmakerMatchClient.getMatchId(),
                        matchmakerMatchClient.getCreated().atOffset(ZoneOffset.UTC),
                        matchmakerMatchClient.getModified().atOffset(ZoneOffset.UTC),
                        matchmakerMatchClient.getUserId(),
                        matchmakerMatchClient.getClientId(),
                        matchmakerMatchClient.getGroupName(),
                        getConfigString(matchmakerMatchClient),
                        matchmakerMatchClient.getDeleted()
                ),
                () -> new MatchmakerMatchClientCreatedEventBodyModel(
                        matchmakerMatchClient.getMatchmakerId(),
                        matchmakerMatchClient.getMatchId(),
                        matchmakerMatchClient.getId()),
                () -> null
        );
    }

    String getConfigString(final MatchmakerMatchClientModel matchClient) {
        try {
            return objectMapper.writeValueAsString(matchClient.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.OBJECT_WRONG, e.getMessage(), e);
        }
    }
}
