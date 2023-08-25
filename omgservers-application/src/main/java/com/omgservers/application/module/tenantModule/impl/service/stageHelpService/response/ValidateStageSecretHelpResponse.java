package com.omgservers.application.module.tenantModule.impl.service.stageHelpService.response;

import com.omgservers.model.stage.StageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateStageSecretHelpResponse {

    StageModel stage;
}
