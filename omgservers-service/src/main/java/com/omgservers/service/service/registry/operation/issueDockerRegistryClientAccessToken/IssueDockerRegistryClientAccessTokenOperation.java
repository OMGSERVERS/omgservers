package com.omgservers.service.service.registry.operation.issueDockerRegistryClientAccessToken;

import com.omgservers.service.service.registry.dto.DockerRegistryAccessDto;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

public interface IssueDockerRegistryClientAccessTokenOperation {

    JsonWebToken issueDockerRegistryClientAccessToken(Long userId, List<DockerRegistryAccessDto> access);
}
