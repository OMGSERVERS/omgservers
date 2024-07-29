package com.omgservers.schema.service.registry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DockerRegistryRepositoryDto {

    String namespace;
    Long tenantId;
    Long projectId;
    Long stageId;
    DockerRegistryContainerQualifierEnum container;

    @Override
    public String toString() {
        return String.format("%s/%d/%d/%d/%s", namespace, tenantId, projectId, stageId, container.getQualifier());
    }
}