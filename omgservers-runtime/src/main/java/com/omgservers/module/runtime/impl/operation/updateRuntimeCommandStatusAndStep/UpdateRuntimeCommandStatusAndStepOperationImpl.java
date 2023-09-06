package com.omgservers.module.runtime.impl.operation.updateRuntimeCommandStatusAndStep;

import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChange.ExecuteChangeOperation;
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
class UpdateRuntimeCommandStatusAndStepOperationImpl implements UpdateRuntimeCommandStatusAndStepOperation {

    final ExecuteChangeOperation executeChangeOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> updateRuntimeCommandStatusAndStep(final ChangeContext<?> changeContext,
                                                          final SqlConnection sqlConnection,
                                                          final int shard,
                                                          final Long id,
                                                          final RuntimeCommandStatusEnum status,
                                                          final Long step) {
        return executeChangeOperation.executeChange(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_runtime_command
                        set modified = $2, status = $3, step = $4
                        where id = $1
                        """,
                Arrays.asList(
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC),
                        status,
                        step
                ),
                () -> null,
                () -> logModelFactory.create(String.format("Status and step of runtime command was updated, " +
                        "id=%d, status=%s, step=%d", id, status, step))
        );
    }
}
