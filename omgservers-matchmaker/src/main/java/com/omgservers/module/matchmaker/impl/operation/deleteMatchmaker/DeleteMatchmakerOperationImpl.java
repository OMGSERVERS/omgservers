package com.omgservers.module.matchmaker.impl.operation.deleteMatchmaker;

import com.omgservers.model.event.body.MatchmakerDeletedEventBodyModel;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChange.ExecuteChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteMatchmakerOperationImpl implements DeleteMatchmakerOperation {

    final ExecuteChangeOperation executeChangeOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteMatchmaker(final ChangeContext<?> changeContext,
                                         final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long id) {
        return executeChangeOperation.executeChange(
                changeContext, sqlConnection, shard,
                """
                        delete from $schema.tab_matchmaker
                        where id = $1
                        """,
                Collections.singletonList(id),
                () -> new MatchmakerDeletedEventBodyModel(id),
                () -> logModelFactory.create("Matchmaker was deleted, id=" + id)
        );
    }
}
