package com.omgservers.schema.entrypoint.registry.handleEvents;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleEventsRegistryRequest {

    @NotNull
    List<DockerRegistryEventDto> events;
}
