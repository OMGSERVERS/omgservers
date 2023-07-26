package com.omgservers.platforms.integrationtest.operations.createVersionAndSignUpOperation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VersionParameters {

    final Long tenant;
    final Long developerUser;
    final String developerPassword;
    final Long project;
    final Long stage;
    final String secret;
    final Long version;
    final Long playerUser;
    final String playerPassword;
}
