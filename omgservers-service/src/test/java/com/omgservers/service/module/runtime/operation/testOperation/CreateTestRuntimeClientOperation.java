package com.omgservers.service.module.runtime.operation.testOperation;

import com.omgservers.model.runtimeClient.RuntimeClientModel;

public interface CreateTestRuntimeClientOperation {

    RuntimeClientModel createTestRuntimeClientOperation(Long runtimeId,
                                                        Long userId,
                                                        Long clientId);
}
