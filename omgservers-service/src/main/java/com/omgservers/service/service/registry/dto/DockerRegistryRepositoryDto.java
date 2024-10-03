package com.omgservers.service.service.registry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DockerRegistryRepositoryDto {

    String namespace;
    Long tenantId;
    Long tenantProjectId;
    DockerRegistryContainerQualifierEnum container;

    @Override
    public String toString() {
        return String.format("%s/%d/%d/%s", namespace, tenantId, tenantProjectId, container.getQualifier());
    }
}