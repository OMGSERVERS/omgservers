package com.omgservers.service.module.matchmaker.operation.testOperation;

public interface CreateTestMatchmakerOperation {

    CreateTestMatchmakerOperationImpl.TestMatchmakerHolder createTestMatchmaker(Long tenantId, Long versionId);
}
