package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindMatchmakerMatchRuntimeRefResponse {

    MatchmakerMatchRuntimeRefModel matchRuntimeRef;
}
