package com.omgservers.service.module.user.operation.testOperation;

public interface CreateTestClientOperation {

    TestClientHolder createTestClient(Long tenantId,
                                      Long stageId,
                                      Long versionId,
                                      Long defaultMatchmakerId,
                                      Long defaultRuntimeId);
}
