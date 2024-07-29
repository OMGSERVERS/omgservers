package com.omgservers.service.server.service.registry.operation.issueDockerRegistryClientRefreshToken;

import org.eclipse.microprofile.jwt.JsonWebToken;

public interface IssueDockerRegistryClientRefreshTokenOperation {

    JsonWebToken issueDockerRegistryClientRefreshToken(Long userId);
}
