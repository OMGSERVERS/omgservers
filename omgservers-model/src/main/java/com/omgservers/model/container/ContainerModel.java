package com.omgservers.model.container;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainerModel {

    @NotNull
    Long id;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @EqualsAndHashCode.Exclude
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
