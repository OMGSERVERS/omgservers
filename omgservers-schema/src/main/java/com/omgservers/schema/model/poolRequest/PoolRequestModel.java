package com.omgservers.schema.model.poolRequest;

import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoolRequestModel {

    @NotNull
    Long id;

    @NotBlank
    @ToString.Exclude
    String idempotencyKey;

    @NotNull
    Long poolId;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    Long runtimeId;

    @NotNull
    RuntimeQualifierEnum runtimeQualifier;

    @Valid
    @NotNull
    @ToString.Exclude
    PoolRequestConfigDto config;

    @NotNull
    Boolean deleted;
}
