package com.omgservers.service.server.registry.operation;

import com.omgservers.service.server.registry.dto.RegistryResourceAccess;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

public interface IssueRegistryAccessTokenOperation {

    JsonWebToken execute(Long userId, List<RegistryResourceAccess> intersection);
}
