package com.omgservers.service.module.runtime.impl.operation.deleteRuntimeClient;

import com.omgservers.service.factory.LogModelFactory;
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
class DeleteRuntimeClientOperationImpl implements DeleteRuntimeClientOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteRuntimeClient(final ChangeContext<?> changeContext,
                                            final SqlConnection sqlConnection,
                                            final int shard,
                                            final Long runtimeId,
                                            final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_runtime_client
                        set modified = $3, deleted = true
                        where runtime_id = $1 and id = $2 and deleted = false
                        """,
                Arrays.asList(
                        runtimeId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)),
                () -> null,
                () -> null
        );
    }
}
