package com.omgservers.dto.tenantModule;

import com.omgservers.model.stage.StageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStageInternalResponse {

    StageModel stage;
}
