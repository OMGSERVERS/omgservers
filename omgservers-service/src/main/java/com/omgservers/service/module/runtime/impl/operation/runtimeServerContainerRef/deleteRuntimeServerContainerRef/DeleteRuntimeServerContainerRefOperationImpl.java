package com.omgservers.service.module.runtime.impl.operation.runtimeServerContainerRef.deleteRuntimeServerContainerRef;

import com.omgservers.model.event.body.module.runtime.RuntimeServerContainerRefDeletedEventBodyModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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
class DeleteRuntimeServerContainerRefOperationImpl implements DeleteRuntimeServerContainerRefOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteRuntimeServerContainerRef(final ChangeContext<?> changeContext,
                                                        final SqlConnection sqlConnection,
                                                        final int shard,
                                                        final Long runtimeId,
                                                        final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_runtime_server_container_ref
                        set modified = $3, deleted = true
                        where runtime_id = $1 and id = $2 and deleted = false
                        """,
                List.of(
                        runtimeId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)),
                () -> new RuntimeServerContainerRefDeletedEventBodyModel(runtimeId, id),
                () -> null
        );
    }
}
