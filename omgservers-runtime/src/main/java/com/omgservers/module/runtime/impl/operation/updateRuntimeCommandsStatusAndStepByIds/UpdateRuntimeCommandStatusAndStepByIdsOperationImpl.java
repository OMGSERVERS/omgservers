package com.omgservers.module.runtime.impl.operation.updateRuntimeCommandsStatusAndStepByIds;

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
class UpdateRuntimeCommandStatusAndStepByIdsOperationImpl implements UpdateRuntimeCommandStatusAndStepByIdsOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> updateRuntimeCommandStatusAndStepByIds(final ChangeContext<?> changeContext,
                                                               final SqlConnection sqlConnection,
                                                               final int shard,
                                                               final List<Long> ids,
                                                               final RuntimeCommandStatusEnum status,
                                                               final Long step) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_runtime_command
                        set modified = $2, status = $3, step = $4
                        where id = any($1)
                        """,
                Arrays.asList(
                        ids.toArray(),
                        Instant.now().atOffset(ZoneOffset.UTC),
                        status,
                        step
                ),
                () -> null,
                () -> logModelFactory.create(String.format("Status and step of runtime commands were updated, " +
                        "status=%s, step=%d, ids=%s,", status, step, ids))
        );
    }
}
