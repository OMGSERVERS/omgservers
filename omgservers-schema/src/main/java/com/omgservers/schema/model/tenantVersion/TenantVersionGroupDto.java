package com.omgservers.schema.model.tenantVersion;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantVersionGroupDto {

    @NotEmpty
    @Size(max = 64)
    String name;

    @NotNull
    @Positive
    Integer minPlayers;

    @NotNull
    @Positive
    Integer maxPlayers;
}
