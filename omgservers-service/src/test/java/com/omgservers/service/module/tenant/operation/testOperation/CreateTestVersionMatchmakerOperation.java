package com.omgservers.service.module.tenant.operation.testOperation;

import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;

public interface CreateTestVersionMatchmakerOperation {

    VersionMatchmakerRefModel createTestVersionMatchmaker(Long tenantId, Long versionId, Long matchmakerId);
}
