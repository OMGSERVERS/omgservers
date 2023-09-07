package com.omgservers.module.runtime.impl.operation.deleteRuntimeCommand;

import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteRuntimeCommandOperationImpl implements DeleteRuntimeCommandOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteRuntimeCommand(final ChangeContext<?> changeContext,
                                             final SqlConnection sqlConnection,
                                             final int shard,
                                             final Long runtimeId,
                                             final Long id) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, shard,
                """
                        delete from $schema.tab_runtime_command
                        where id = $1
                        """,
                Collections.singletonList(id),
                () -> null,
                () -> logModelFactory.create(String.format("Runtime command was deleted, " +
                        "runtimeId=%d, id=%d", runtimeId, id))
        );
    }
}
