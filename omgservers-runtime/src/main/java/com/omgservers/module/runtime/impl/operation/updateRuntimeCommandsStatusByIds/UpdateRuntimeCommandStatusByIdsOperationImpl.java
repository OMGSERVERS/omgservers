package com.omgservers.module.runtime.impl.operation.updateRuntimeCommandsStatusByIds;

import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
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
class UpdateRuntimeCommandStatusByIdsOperationImpl implements UpdateRuntimeCommandStatusByIdsOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> updateRuntimeCommandStatusByIds(final ChangeContext<?> changeContext,
                                                        final SqlConnection sqlConnection,
                                                        final int shard,
                                                        final Long runtimeId,
                                                        final List<Long> ids,
                                                        final RuntimeCommandStatusEnum status) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_runtime_command
                        set modified = $3, status = $4
                        where runtime_id = $1 and id = any($2)
                        """,
                Arrays.asList(
                        runtimeId,
                        ids.toArray(),
                        Instant.now().atOffset(ZoneOffset.UTC),
                        status
                ),
                () -> null,
                () -> logModelFactory.create(String.format("Status for runtime commands were updated, " +
                        "status=%s, ids=%s", status, ids))
        );
    }
}
