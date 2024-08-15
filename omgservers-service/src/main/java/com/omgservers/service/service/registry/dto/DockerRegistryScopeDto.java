package com.omgservers.service.service.registry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class DockerRegistryScopeDto {

    List<DockerRegistryResourceScopeDto> resourceScopes;

    public DockerRegistryScopeDto() {
        resourceScopes = new ArrayList<>();
    }
}
