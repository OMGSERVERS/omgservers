package com.omgservers.service.module.matchmaker.operation.testOperation;

import com.omgservers.model.matchmaker.MatchmakerModel;

public interface CreateTestMatchmakerOperation {

    MatchmakerModel createTestMatchmaker(Long tenantId, Long versionId);
}
