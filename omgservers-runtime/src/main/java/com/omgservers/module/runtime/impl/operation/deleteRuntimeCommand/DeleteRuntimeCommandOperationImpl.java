package com.omgservers.module.runtime.impl.operation.deleteRuntimeCommand;

import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteRuntimeCommandOperationImpl implements DeleteRuntimeCommandOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteRuntimeCommand(final ChangeContext<?> changeContext,
                                             final SqlConnection sqlConnection,
                                             final int shard,
                                             final Long runtimeId,
                                             final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """                        
                        update $schema.tab_runtime_command
                        set deleted = true
                        where runtime_id = $1 and id = $2 and deleted = false
                        """,
                Arrays.asList(runtimeId, id),
                () -> null,
                () -> logModelFactory.create(String.format("Runtime command was deleted, " +
                        "runtimeId=%d, id=%d", runtimeId, id))
        );
    }
}
