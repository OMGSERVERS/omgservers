package com.omgservers.model.project;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectModel {

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Boolean deleted;
}
