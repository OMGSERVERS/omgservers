package com.omgservers.service.service.registry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class DockerRegistryResourceScopeDto {

    DockerRegistryResourceTypeEnum resourceType;
    DockerRegistryResourceNameDto resourceName;
    List<DockerRegistryActionEnum> actions;

    public DockerRegistryResourceScopeDto() {
        actions = new ArrayList<>();
    }
}
