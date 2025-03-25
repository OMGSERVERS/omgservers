package com.omgservers.schema.model.runtimeCommand;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = RuntimeCommandDeserializer.class)
public class RuntimeCommandModel {

    @NotNull
    Long id;

    @NotBlank
    @ToString.Exclude
    String idempotencyKey;

    @NotNull
    Long runtimeId;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    RuntimeCommandQualifierEnum qualifier;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    RuntimeCommandBodyDto body;

    @NotNull
    Boolean deleted;
}
