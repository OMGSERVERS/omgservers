package com.omgservers.schema.module.tenant.stage;

import com.omgservers.schema.module.tenant.stage.dto.StageDataDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStageDataResponse {

    @NotNull
    StageDataDto stageData;
}
