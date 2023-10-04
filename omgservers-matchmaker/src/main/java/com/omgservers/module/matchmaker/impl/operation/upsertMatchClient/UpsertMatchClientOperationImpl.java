package com.omgservers.module.matchmaker.impl.operation.upsertMatchClient;

import com.omgservers.model.event.body.MatchClientCreatedEventBodyModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertMatchClientOperationImpl implements UpsertMatchClientOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertMatchClient(final ChangeContext<?> changeContext,
                                          final SqlConnection sqlConnection,
                                          final int shard,
                                          final MatchClientModel matchClient) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_matchmaker_match_client(
                            id, matchmaker_id, match_id, created, modified, user_id, client_id)
                        values($1, $2, $3, $4, $5, $6, $7)
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
                        matchClient.getClientId()
                ),
                () -> new MatchClientCreatedEventBodyModel(
                        matchClient.getMatchmakerId(),
                        matchClient.getMatchId(),
                        matchClient.getId()),
                () -> logModelFactory.create("Match client was inserted, matchClient=" + matchClient)
        );
    }
}
