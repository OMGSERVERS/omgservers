package com.omgservers.model.dto.tenant;

import com.omgservers.model.stage.StageModel;
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
