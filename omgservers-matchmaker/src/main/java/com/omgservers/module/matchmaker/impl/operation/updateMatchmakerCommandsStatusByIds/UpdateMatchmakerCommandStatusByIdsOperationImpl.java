package com.omgservers.module.matchmaker.impl.operation.updateMatchmakerCommandsStatusByIds;

import com.omgservers.model.matchmakerCommand.MatchmakerCommandStatusEnum;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateMatchmakerCommandStatusByIdsOperationImpl implements UpdateMatchmakerCommandStatusByIdsOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> updateMatchmakerCommandStatusByIds(final ChangeContext<?> changeContext,
                                                           final SqlConnection sqlConnection,
                                                           final int shard,
                                                           final Long matchmakerId,
                                                           final List<Long> ids,
                                                           final MatchmakerCommandStatusEnum status) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_matchmaker_command
                        set modified = $3, status = $4
                        where matchmaker_id = $1 and id = any($2)
                        """,
                Arrays.asList(
                        matchmakerId,
                        ids.toArray(),
                        Instant.now().atOffset(ZoneOffset.UTC),
                        status
                ),
                () -> null,
                () -> logModelFactory.create(String.format("Status for matchmaker commands were updated, " +
                        "status=%s, ids=%s", status, ids))
        );
    }
}
