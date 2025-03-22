package com.omgservers.service.service.registry.operation;

import com.omgservers.service.service.registry.dto.ParsedResourceScope;
import com.omgservers.service.service.registry.dto.RegistryResourceAccess;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface IntersectScopeAndPermissionsOperation {

    Uni<List<RegistryResourceAccess>> execute(Long userId,
                                              List<ParsedResourceScope> parsedScope);
}
