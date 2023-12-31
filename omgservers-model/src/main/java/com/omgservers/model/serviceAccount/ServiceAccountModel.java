package com.omgservers.model.serviceAccount;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAccountModel {

    @NotNull
    Long id;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotBlank
    @Size(max = 64)
    String username;

    @NotBlank
    @Size(max = 1024)
    @ToString.Exclude
    String passwordHash;

    @NotNull
    Boolean deleted;
}
