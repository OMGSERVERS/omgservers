package com.omgservers.service.server.registry.operation;

import com.omgservers.service.server.registry.dto.ParsedResourceScope;

public interface ParseResourceScopeOperation {

    ParsedResourceScope execute(String scope);
}
