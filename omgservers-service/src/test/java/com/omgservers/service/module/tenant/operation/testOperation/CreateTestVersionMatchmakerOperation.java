package com.omgservers.service.module.tenant.operation.testOperation;

import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;

public interface CreateTestVersionMatchmakerOperation {

    VersionMatchmakerModel createTestVersionMatchmaker(Long tenantId, Long versionId, Long matchmakerId);
}
