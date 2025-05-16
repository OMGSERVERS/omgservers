package com.omgservers.schema.model.tenantVersion;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantVersionModeDto {

    @NotBlank
    @Size(max = 64)
    String name;

    @NotNull
    @Positive
    Integer minPlayers;

    @NotNull
    @Positive
    Integer maxPlayers;

    @Valid
    @NotEmpty
    List<TenantVersionGroupDto> groups;
}
