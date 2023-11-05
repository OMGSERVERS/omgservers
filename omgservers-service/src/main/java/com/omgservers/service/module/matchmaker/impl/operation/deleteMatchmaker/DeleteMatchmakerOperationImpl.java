package com.omgservers.service.module.matchmaker.impl.operation.deleteMatchmaker;

import com.omgservers.model.event.body.MatchmakerDeletedEventBodyModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.module.matchmaker.impl.operation.selectMatchmaker.SelectMatchmakerOperation;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteMatchmakerOperationImpl implements DeleteMatchmakerOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectMatchmakerOperation selectMatchmakerOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteMatchmaker(final ChangeContext<?> changeContext,
                                         final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_matchmaker
                        set modified = $2, deleted = true
                        where id = $1 and deleted = false
                        """,
                Arrays.asList(
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new MatchmakerDeletedEventBodyModel(id),
                () -> logModelFactory.create("Matchmaker was deleted, id=" + id)
        );
    }
}
