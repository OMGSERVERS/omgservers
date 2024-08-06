package com.omgservers.schema.entrypoint.registry.handleEvents;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DockerRegistryEventDto {

    @NotNull
    String id;

    @NotNull
    OffsetDateTime timestamp;

    @NotNull
    String action;

    @NotNull
    TargetRecordDto target;

    @NotNull
    RequestRecordDto request;

    @NotNull
    ActorRecordDto actor;

    @NotNull
    SourceRecordDto source;
}
