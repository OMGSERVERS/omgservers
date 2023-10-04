package com.omgservers.module.matchmaker.impl.operation.upsertMatchmaker;

import com.omgservers.model.event.body.MatchmakerCreatedEventBodyModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
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
class UpsertMatchmakerOperationImpl implements UpsertMatchmakerOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertMatchmaker(final ChangeContext<?> changeContext,
                                         final SqlConnection sqlConnection,
                                         final int shard,
                                         final MatchmakerModel matchmaker) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_matchmaker(id, created, modified, tenant_id, stage_id)
                        values($1, $2, $3, $4, $5)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        matchmaker.getId(),
                        matchmaker.getCreated().atOffset(ZoneOffset.UTC),
                        matchmaker.getModified().atOffset(ZoneOffset.UTC),
                        matchmaker.getTenantId(),
                        matchmaker.getStageId()
                ),
                () -> new MatchmakerCreatedEventBodyModel(matchmaker.getId()),
                () -> logModelFactory.create("Matchmaker was inserted, matchmaker=" + matchmaker)
        );
    }
}
