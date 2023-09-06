package com.omgservers.module.runtime.impl.operation.updateRuntimeCurrentStep;

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
class UpdateRuntimeCurrentStepOperationImpl implements UpdateRuntimeCurrentStepOperation {

    final ExecuteChangeOperation executeChangeOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> updateRuntimeCurrentStep(final ChangeContext<?> changeContext,
                                                 final SqlConnection sqlConnection,
                                                 final int shard,
                                                 final Long id,
                                                 final Long currentStep) {
        return executeChangeOperation.executeChange(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_runtime
                        set modified = $2, current_step = $3
                        where id = $1
                        """,
                Arrays.asList(
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC),
                        currentStep
                ),
                () -> null,
                () -> logModelFactory.create(String.format("Current step of runtime was updated, " +
                        "id=%d, step=%d", id, currentStep))
        );
    }
}
