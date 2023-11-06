package com.omgservers.model.runtimeGrant;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeGrantModel {

    @NotNull
    Long id;

    @NotNull
    Long runtimeId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long shardKey;

    @NotNull
    Long entityId;

    @NotNull
    RuntimeGrantTypeEnum type;

    @NotNull
    Boolean deleted;
}
