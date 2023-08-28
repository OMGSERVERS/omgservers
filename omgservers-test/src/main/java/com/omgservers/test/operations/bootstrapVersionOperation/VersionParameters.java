package com.omgservers.test.operations.bootstrapVersionOperation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VersionParameters {

    final Long tenantId;
    final Long developerUser;
    final String developerPassword;
    final Long projectId;
    final Long stageId;
    final String stageSecret;
    final Long versionId;
}
