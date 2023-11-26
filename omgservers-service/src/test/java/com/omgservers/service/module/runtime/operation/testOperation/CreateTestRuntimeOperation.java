package com.omgservers.service.module.runtime.operation.testOperation;

public interface CreateTestRuntimeOperation {

    CreateTestRuntimeOperationImpl.TestRuntimeHolder createTestLobby(Long tenantId, Long versionId);
}
