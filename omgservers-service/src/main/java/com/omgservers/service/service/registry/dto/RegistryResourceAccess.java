package com.omgservers.service.service.registry.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record RegistryResourceAccess(RegistryResourceTypeEnum resourceType,
                                     String resourceName,
                                     List<RegistryActionEnum> actions) {

    static public RegistryResourceAccess buildNoAccess(final ParsedResourceScope resourceScope) {
        return new RegistryResourceAccess(resourceScope.resourceType(),
                resourceScope.buildResourceName(),
                new ArrayList<>());
    }

    static public RegistryResourceAccess buildReadAccess(final ParsedResourceScope resourceScope) {
        return new RegistryResourceAccess(resourceScope.resourceType(),
                resourceScope.buildResourceName(),
                List.of(RegistryActionEnum.PULL));
    }

    static public RegistryResourceAccess buildWriteAccess(final ParsedResourceScope resourceScope) {
        return new RegistryResourceAccess(resourceScope.resourceType(),
                resourceScope.buildResourceName(),
                List.of(RegistryActionEnum.PULL, RegistryActionEnum.PUSH));
    }

    public String buildScopeString() {
        final var actionsString = actions.stream()
                .map(RegistryActionEnum::getAction)
                .collect(Collectors.joining(","));
        return String.format("%s:%s:%s", resourceType.getType(), resourceName, actionsString);
    }
}
