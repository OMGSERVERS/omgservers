package com.omgservers.schema.module.tenant.stage.dto;

import com.omgservers.schema.model.stage.StageModel;
import com.omgservers.schema.model.version.VersionProjectionModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StageDataDto {

    @NotNull
    StageModel stage;

    @NotNull
    List<VersionProjectionModel> versionProjections;
}
