package com.omgservers.service.module.tenant.operation.testOperation;

import com.omgservers.model.versionLobbyRef.VersionLobbyRefModel;

public interface CreateTestVersionRuntimeOperation {

    VersionLobbyRefModel createTestVersionRuntime(Long tenantId, Long versionId, Long runtimeId);
}
