package com.omgservers.model.job;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobModel {

    @NotNull
    Long id;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long shardKey;

    @NotNull
    Long entityId;

    @NotNull
    JobQualifierEnum qualifier;

    @NotNull
    Boolean deleted;
}
