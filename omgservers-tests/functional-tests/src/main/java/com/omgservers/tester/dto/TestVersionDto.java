package com.omgservers.tester.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestVersionDto {

    String adminToken;

    Long supportUserId;
    String supportPassword;
    String supportToken;

    Long developerUserId;
    String developerPassword;
    String developerToken;

    Long tenantId;
    Long tenantProjectId;
    Long tenantStageId;
    String tenantStageSecret;
    Long tenantVersionId;
    Long tenantFilesArchiveId;
    Long tenantDeploymentId;
}
