package com.omgservers.tester.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestVersionModel {

    String adminToken;

    Long supportUserId;
    String supportPassword;
    String supportToken;

    Long tenantId;
    Long developerUserId;
    String developerPassword;
    String developerToken;
    Long projectId;
    Long stageId;
    String stageSecret;
    Long versionId;
}
