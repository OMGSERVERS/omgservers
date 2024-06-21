package com.omgservers.model.dto.tenant;

import com.omgservers.model.stage.StageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStageResponse {

    StageModel stage;
}
