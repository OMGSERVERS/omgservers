package com.omgservers.service.module.runtime.impl.operation.deleteRuntimeGrant;

import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.runtime.impl.operation.selectRuntimeGrant.SelectRuntimeGrantOperation;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteRuntimeGrantOperationImpl implements DeleteRuntimeGrantOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectRuntimeGrantOperation selectRuntimeGrantOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteRuntimeGrant(final ChangeContext<?> changeContext,
                                           final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long runtimeId,
                                           final Long id) {
        return selectRuntimeGrantOperation.selectRuntimeGrant(sqlConnection, shard, runtimeId, id)
                .flatMap(runtimeGrant -> changeObjectOperation.changeObject(
                        changeContext, sqlConnection, shard,
                        """
                                delete from $schema.tab_runtime_grant
                                where runtime_id = $1 and id = $2
                                """,
                        Arrays.asList(runtimeGrant.getRuntimeId(), id),
                        () -> null,
                        () -> logModelFactory.create("Runtime grant was deleted, runtimeGrant=" + runtimeGrant)
                ))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(false);
    }
}
