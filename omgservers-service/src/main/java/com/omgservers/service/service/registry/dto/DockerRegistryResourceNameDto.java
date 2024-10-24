package com.omgservers.service.service.registry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DockerRegistryResourceNameDto {

    String hostname;
    DockerRegistryRepositoryDto repository;
}
