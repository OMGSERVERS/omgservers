package com.omgservers.module.runtime.impl.operation.updateRuntimeStepAndState;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.runtime.RuntimeStateModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateRuntimeStepAndStateOperationImpl implements UpdateRuntimeStepAndStateOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> updateRuntimeStepAndState(final ChangeContext<?> changeContext,
                                                  final SqlConnection sqlConnection,
                                                  final int shard,
                                                  final Long id,
                                                  final Long step,
                                                  final RuntimeStateModel state) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_runtime
                        set modified = $2, step = $3, state = $4
                        where id = $1
                        """,
                Arrays.asList(
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC),
                        step,
                        getStateString(state)
                ),
                () -> null,
                () -> logModelFactory.create(String.format("Current step of runtime was updated, " +
                        "id=%d, step=%d", id, step))
        );
    }

    String getStateString(RuntimeStateModel state) {
        try {
            return objectMapper.writeValueAsString(state);
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
