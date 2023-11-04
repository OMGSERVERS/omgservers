package com.omgservers.model.container;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainerModel {

    @NotNull
    Long id;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long entityId;

    @NotNull
    ContainerQualifierEnum qualifier;

    @NotNull
    String image;

    @NotNull
    ContainerConfigModel config;

    @NotNull
    Boolean deleted;
}
