package com.omgservers.model.version;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionProjectionModel {

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Boolean deleted;
}
