package com.omgservers.service.service.registry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DockerRegistryRepositoryDto {

    String namespace;
    String tenant;
    String project;
    DockerRegistryContainerQualifierEnum container;

    @Override
    public String toString() {
        return String.format("%s/%s/%s/%s", namespace, tenant, project, container.getQualifier());
    }
}