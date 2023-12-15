package com.omgservers.service.module.runtime.operation.testOperation;

import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.service.factory.RuntimeClientModelFactory;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimeClientOperationTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CreateTestRuntimeClientOperationImpl implements CreateTestRuntimeClientOperation {

    final UpsertRuntimeClientOperationTestInterface upsertRuntimeClientOperation;

    final RuntimeClientModelFactory runtimeClientModelFactory;

    @Override
    public RuntimeClientModel createTestRuntimeClientOperation(final Long runtimeId,
                                                               final Long userId,
                                                               final Long clientId) {
        final var shard = 0;

        final var runtimeClient = runtimeClientModelFactory.create(runtimeId,
                userId,
                clientId);

        upsertRuntimeClientOperation.upsertRuntimeClient(shard, runtimeClient);

        return runtimeClient;
    }
}
