package com.omgservers.module.matchmaker.impl.operation.deleteMatchmaker;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.event.body.MatchmakerDeletedEventBodyModel;
import com.omgservers.module.matchmaker.impl.operation.selectMatchmaker.SelectMatchmakerOperation;
import com.omgservers.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
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

    final ChangeObjectOperation changeObjectOperation;
    final SelectMatchmakerOperation selectMatchmakerOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteMatchmaker(final ChangeContext<?> changeContext,
                                         final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long id) {
        return selectMatchmakerOperation.selectMatchmaker(sqlConnection, shard, id)
                .flatMap(matchmaker -> changeObjectOperation.changeObject(
                        changeContext, sqlConnection, shard,
                        """
                                delete from $schema.tab_matchmaker
                                where id = $1
                                """,
                        Collections.singletonList(id),
                        () -> new MatchmakerDeletedEventBodyModel(matchmaker),
                        () -> logModelFactory.create("Matchmaker was deleted, matchmaker=" + matchmaker)
                ))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(false);
    }
}
