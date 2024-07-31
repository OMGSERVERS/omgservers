package com.omgservers.schema.entrypoint.developer.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StageDashboardDto {

    @NotNull
    StageDto stage;

    @NotNull
    List<VersionProjectionDto> versionProjections;
}
