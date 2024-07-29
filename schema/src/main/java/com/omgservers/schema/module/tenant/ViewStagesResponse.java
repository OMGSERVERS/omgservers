package com.omgservers.schema.module.tenant;

import com.omgservers.schema.model.stage.StageModel;
import com.omgservers.schema.model.stage.StageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewStagesResponse {

    List<StageModel> stages;
}
