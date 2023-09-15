package com.omgservers.model.script;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScriptConfigModel {

    @NotNull
    Long userId;

    @NotNull
    Long playerId;

    @NotNull
    Long clientId;

    @NotNull
    Long matchmakerId;

    @NotNull
    Long matchId;

    @NotNull
    Long runtimeId;
}
