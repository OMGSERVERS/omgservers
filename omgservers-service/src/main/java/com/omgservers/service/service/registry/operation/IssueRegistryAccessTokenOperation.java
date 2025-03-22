package com.omgservers.service.service.registry.operation;

import com.omgservers.service.service.registry.dto.RegistryResourceAccess;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

public interface IssueRegistryAccessTokenOperation {

    JsonWebToken execute(Long userId, List<RegistryResourceAccess> intersection);
}
