package com.omgservers.schema.model.poolSeverContainer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
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
public class PoolContainerModel {

    @NotNull
    Long id;

    @NotBlank
    @ToString.Exclude
    String idempotencyKey;

    @NotNull
    Long poolId;

    @NotNull
    Long serverId;

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

    @NotNull
    @ToString.Exclude
    PoolContainerConfigDto config;

    @NotNull
    Boolean deleted;

    @JsonIgnore
    public String getContainerName() {
        return runtimeQualifier.toString().toLowerCase() + "_" + id;
    }
}
