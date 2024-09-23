package com.omgservers.service.service.registry.operation.parseDockerRegistryScope;

import com.omgservers.service.service.registry.dto.DockerRegistryActionEnum;
import com.omgservers.service.service.registry.dto.DockerRegistryResourceNameDto;
import com.omgservers.service.service.registry.dto.DockerRegistryResourceScopeDto;
import com.omgservers.service.service.registry.dto.DockerRegistryResourceTypeEnum;
import com.omgservers.service.service.registry.dto.DockerRegistryScopeDto;
import com.omgservers.service.operation.parseDockerRepository.ParseDockerRepositoryOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ParseDockerRegistryScopeOperationImpl implements ParseDockerRegistryScopeOperation {

    final ParseDockerRepositoryOperation parseDockerRepositoryOperation;

    @Override
    public DockerRegistryScopeDto parseDockerRegistryScope(final String scope) {
        final var dockerRegistryScopeDto = new DockerRegistryScopeDto();

        if (Objects.isNull(scope) || scope.isEmpty()) {
            return dockerRegistryScopeDto;
        }

        final var resourceScopeStrings = scope.split(" ");
        for (final var resourceScopeString : resourceScopeStrings) {
            final var resourceScopeParts = resourceScopeString.split(":");
            if (resourceScopeParts.length < 3) {
                log.debug("Resource scope is ignored, resource scope has wrong structure, " +
                        "resourceScope={}", resourceScopeString);
                continue;
            }

            final var resourceScope = new DockerRegistryResourceScopeDto();

            // ResourceType parsing
            final var resourceTypeDelimiterIndex = resourceScopeString.indexOf(":");
            if (resourceTypeDelimiterIndex == -1) {
                log.debug("Resource scope is ignored, resource type delimiter wasn't found, " +
                        "resourceScope={}", resourceScopeString);
                continue;
            }
            final var resourceTypeString = resourceScopeString.substring(0, resourceTypeDelimiterIndex);
            final var resourceType = DockerRegistryResourceTypeEnum.fromString(resourceTypeString);
            resourceScope.setResourceType(resourceType);

            // ResourceName parsing
            final var resourceNameDelimiterIndex = resourceScopeString.lastIndexOf(":");
            if (resourceNameDelimiterIndex == -1) {
                log.debug("Resource scope is ignored, resource name delimiter wasn't found, " +
                        "resourceScope={}", resourceScopeString);
                continue;
            }
            final var resourceNameString = resourceScopeString
                    .substring(resourceTypeDelimiterIndex + 1, resourceNameDelimiterIndex);

            final var resourceName = new DockerRegistryResourceNameDto();
            final var resourceNameParts = resourceNameString.split("/");
            if (resourceNameParts.length == 5) {
                // hostname:5000/omgservers/245515657456648192/231077687903387648/universal
                final var hostnameDelimiterIndex = resourceNameString.indexOf("/");
                if (hostnameDelimiterIndex == -1) {
                    log.debug("Resource scope is ignored, hostname delimiter wasn't found, " +
                            "resourceScope={}", resourceScopeString);
                    continue;
                }
                final var hostnameString = resourceNameString.substring(0, hostnameDelimiterIndex);
                resourceName.setHostname(hostnameString);

                final var repositoryString = resourceNameString.substring(hostnameDelimiterIndex + 1);
                final var repository =
                        parseDockerRepositoryOperation.parseDockerRegistryRepository(repositoryString);
                resourceName.setRepository(repository);
            } else if (resourceNameParts.length == 4) {
                final var repository = parseDockerRepositoryOperation
                        .parseDockerRegistryRepository(resourceNameString);
                resourceName.setRepository(repository);
            } else {
                log.debug("Resource scope is ignored, resource name has wrong structure, " +
                        "resourceScope={}", resourceScopeString);
                continue;
            }
            resourceScope.setResourceName(resourceName);

            // Actions parsing
            final var actionsString = resourceScopeString.substring(resourceNameDelimiterIndex + 1);
            final var actionsStringParts = actionsString.split(",");
            for (final var actionString : actionsStringParts) {
                final var action = DockerRegistryActionEnum.fromString(actionString);
                resourceScope.getActions().add(action);
            }

            dockerRegistryScopeDto.getResourceScopes().add(resourceScope);
        }

        return dockerRegistryScopeDto;
    }
}
