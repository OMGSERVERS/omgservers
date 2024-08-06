package com.omgservers.schema.model.stage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StageModel {

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

    @NotNull
    Long projectId;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotBlank
    String idempotencyKey;

    @NotBlank
    @Size(max = 1024)
    @ToString.Exclude
    String secret;

    @NotNull
    Boolean deleted;
}
