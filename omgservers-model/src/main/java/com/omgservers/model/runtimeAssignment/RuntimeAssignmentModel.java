package com.omgservers.model.runtimeAssignment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeAssignmentModel {

    @NotNull
    Long id;

    @NotBlank
    String idempotencyKey;

    @NotNull
    Long runtimeId;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    Long clientId;

    @NotNull
    Instant lastActivity;

    @NotNull
    RuntimeAssignmentConfigModel config;

    @NotNull
    Boolean deleted;
}
