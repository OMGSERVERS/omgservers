package com.omgservers.service.shard.matchmaker.impl.operation.matchmaker;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerCreatedEventBodyModel;
import com.omgservers.service.factory.system.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertMatchmakerOperationImpl implements UpsertMatchmakerOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final MatchmakerModel matchmaker) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, shard,
                """
                        insert into $shard.tab_matchmaker(
                            id, idempotency_key, created, modified, deployment_id, deleted)
                        values($1, $2, $3, $4, $5, $6)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        matchmaker.getId(),
                        matchmaker.getIdempotencyKey(),
                        matchmaker.getCreated().atOffset(ZoneOffset.UTC),
                        matchmaker.getModified().atOffset(ZoneOffset.UTC),
                        matchmaker.getDeploymentId(),
                        matchmaker.getDeleted()
                ),
                () -> new MatchmakerCreatedEventBodyModel(matchmaker.getId()),
                () -> null
        );
    }
}
