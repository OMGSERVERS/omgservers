package com.omgservers.schema.entrypoint.developer.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionDashboardDto {

    @NotNull
    VersionDto version;
}
