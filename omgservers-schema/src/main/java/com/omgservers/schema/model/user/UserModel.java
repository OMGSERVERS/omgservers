package com.omgservers.schema.model.user;

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
    String idempotencyKey;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    UserRoleEnum role;

    @NotBlank
    @Size(max = 1024)
    @ToString.Exclude
    String passwordHash;

    @NotNull
    Boolean deleted;
}
