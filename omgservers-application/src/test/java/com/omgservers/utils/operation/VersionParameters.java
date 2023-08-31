package com.omgservers.utils.operation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VersionParameters {

    final Long tenantId;
    final Long developerUserId;
    final String developerPassword;
    final Long projectId;
    final Long stageId;
    final String stageSecret;
    final Long versionId;
}
