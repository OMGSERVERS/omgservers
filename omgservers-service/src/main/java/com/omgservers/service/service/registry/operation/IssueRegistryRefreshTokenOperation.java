package com.omgservers.service.service.registry.operation;

import org.eclipse.microprofile.jwt.JsonWebToken;

public interface IssueRegistryRefreshTokenOperation {

    JsonWebToken issueDockerRegistryClientRefreshToken(Long userId);
}
