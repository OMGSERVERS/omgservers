package com.omgservers.service.service.registry.operation.issueDockerRegistryClientRefreshToken;

import org.eclipse.microprofile.jwt.JsonWebToken;

public interface IssueDockerRegistryClientRefreshTokenOperation {

    JsonWebToken issueDockerRegistryClientRefreshToken(Long userId);
}
