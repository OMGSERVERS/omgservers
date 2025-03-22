package com.omgservers.service.service.registry.operation;

import com.omgservers.service.service.registry.dto.ParsedResourceScope;

public interface ParseResourceScopeOperation {

    ParsedResourceScope execute(String scope);
}
