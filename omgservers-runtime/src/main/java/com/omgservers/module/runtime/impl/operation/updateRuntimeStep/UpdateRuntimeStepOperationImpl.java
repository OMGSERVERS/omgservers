package com.omgservers.module.runtime.impl.operation.updateRuntimeStep;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.RuntimeUpdatedEventBodyModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
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
class UpdateRuntimeStepOperationImpl implements UpdateRuntimeStepOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> updateRuntimeStep(final ChangeContext<?> changeContext,
                                          final SqlConnection sqlConnection,
                                          final int shard,
                                          final Long id,
                                          final Long step) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_runtime
                        set modified = $2, step = $3
                        where id = $1
                        """,
                Arrays.asList(
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC),
                        step
                ),
                () -> new RuntimeUpdatedEventBodyModel(id),
                () -> logModelFactory.create(String.format("Current step of runtime was updated, " +
                        "id=%d, step=%d", id, step))
        );
    }
}
