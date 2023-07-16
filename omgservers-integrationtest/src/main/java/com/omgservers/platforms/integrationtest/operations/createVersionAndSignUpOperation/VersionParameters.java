package com.omgservers.platforms.integrationtest.operations.createVersionAndSignUpOperation;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class VersionParameters {

    final UUID tenant;
    final UUID developerUser;
    final String developerPassword;
    final UUID project;
    final UUID stage;
    final String secret;
    final UUID version;
    final UUID playerUser;
    final String playerPassword;
}
