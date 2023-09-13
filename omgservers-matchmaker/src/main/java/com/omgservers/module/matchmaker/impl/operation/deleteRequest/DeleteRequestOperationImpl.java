package com.omgservers.module.matchmaker.impl.operation.deleteRequest;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.event.body.RequestDeletedEventBodyModel;
import com.omgservers.module.matchmaker.impl.operation.selectRequest.SelectRequestOperation;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteRequestOperationImpl implements DeleteRequestOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final SelectRequestOperation selectRequestOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteRequest(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final Long matchmakerId,
                                      final Long id) {
        return selectRequestOperation.selectRequest(sqlConnection, shard, matchmakerId, id)
                .flatMap(request -> executeChangeObjectOperation.executeChangeObject(
                        changeContext, sqlConnection, shard,
                        """
                                delete from $schema.tab_matchmaker_request
                                where matchmaker_id = $1 and id = $2
                                """,
                        Arrays.asList(matchmakerId, id),
                        () -> new RequestDeletedEventBodyModel(request),
                        () -> logModelFactory.create("Request was deleted, request=" + request)
                ))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(false);
    }
}
