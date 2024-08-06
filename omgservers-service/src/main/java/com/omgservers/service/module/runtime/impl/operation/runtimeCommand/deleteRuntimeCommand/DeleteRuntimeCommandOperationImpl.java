package com.omgservers.service.module.runtime.impl.operation.runtimeCommand.deleteRuntimeCommand;

import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.server.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

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
                        set modified = $3, deleted = true
                        where runtime_id = $1 and id = $2 and deleted = false
                        """,
                List.of(
                        runtimeId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> null,
                () -> null
        );
    }
}
