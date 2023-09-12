package com.omgservers.model.script;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScriptConfigModel {

    Long userId;
    Long playerId;
    Long clientId;
    Long matchmakerId;
    Long matchId;
    Long runtimeId;
}
