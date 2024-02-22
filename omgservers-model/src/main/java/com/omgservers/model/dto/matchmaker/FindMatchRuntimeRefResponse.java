package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.matchRuntimeRef.MatchRuntimeRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindMatchRuntimeRefResponse {

    MatchRuntimeRefModel matchRuntimeRef;
}
