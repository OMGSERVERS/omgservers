package com.omgservers.service.module.runtime.operation.testOperation;

import com.omgservers.model.runtime.RuntimeModel;

public interface CreateTestRuntimeOperation {

    RuntimeModel createTestRuntime(Long tenantId, Long versionId);
}
