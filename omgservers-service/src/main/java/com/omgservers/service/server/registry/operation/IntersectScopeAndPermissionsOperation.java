package com.omgservers.service.server.registry.operation;

import com.omgservers.service.server.registry.dto.ParsedResourceScope;
import com.omgservers.service.server.registry.dto.RegistryResourceAccess;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface IntersectScopeAndPermissionsOperation {

    Uni<List<RegistryResourceAccess>> execute(Long userId,
                                              List<ParsedResourceScope> parsedScope);
}
