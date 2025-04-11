package com.omgservers.schema.model.user;

import jakarta.validation.Valid;
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
public class UserModel {

    @NotNull
    Long id;

    @NotBlank
    @ToString.Exclude
    String idempotencyKey;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    UserRoleEnum role;

    @NotBlank
    @Size(max = 1024)
    @ToString.Exclude
    String passwordHash;

    @Valid
    @NotNull
    UserConfigDto config;

    @NotNull
    Boolean deleted;
}
