package com.omgservers.schema.service.registry;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class DockerRegistryAccessDto {

    DockerRegistryResourceTypeEnum type;
    String name;
    Set<DockerRegistryActionEnum> actions;

    public DockerRegistryAccessDto() {
        actions = new HashSet<>();
    }

    @Override
    public String toString() {
        final var actionsString = actions.stream()
                .map(DockerRegistryActionEnum::getAction).collect(Collectors.joining(","));
        return String.format("%s:%s:%s", type.getType(), name, actionsString);
    }
}
