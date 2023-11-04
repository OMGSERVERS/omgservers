package com.omgservers.module.runtime.impl.operation.deleteRuntimeCommandsByIds;

import com.omgservers.factory.LogModelFactory;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteRuntimeCommandByIdsOperationImpl implements DeleteRuntimeCommandByIdsOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteRuntimeCommandByIds(final ChangeContext<?> changeContext,
                                                  final SqlConnection sqlConnection,
                                                  final int shard,
                                                  final Long runtimeId,
                                                  final List<Long> ids) {
        return changeObjectOperation.changeObject(changeContext, sqlConnection, shard,
                """
                        update $schema.tab_runtime_command
                        set modified = $3, deleted = true
                        where runtime_id = $1 and id = any($2) and deleted = false
                        """,
                Arrays.asList(
                        runtimeId,
                        ids.toArray(),
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> null,
                () -> logModelFactory.create(String.format("Runtime commands were deleted, " +
                        "runtimeId=%d, ids=%s", runtimeId, ids))
        );
    }
}
