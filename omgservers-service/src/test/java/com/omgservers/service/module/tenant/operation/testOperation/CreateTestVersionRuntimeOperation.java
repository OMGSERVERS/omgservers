package com.omgservers.service.module.tenant.operation.testOperation;

import com.omgservers.model.versionRuntime.VersionRuntimeModel;

public interface CreateTestVersionRuntimeOperation {

    VersionRuntimeModel createTestVersionRuntime(Long tenantId, Long versionId, Long runtimeId);
}
