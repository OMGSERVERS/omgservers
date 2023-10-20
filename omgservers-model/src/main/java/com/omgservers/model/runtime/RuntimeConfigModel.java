package com.omgservers.model.runtime;

import com.omgservers.model.version.VersionModeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeConfigModel {

    static public RuntimeConfigModel create() {
        final var runtimeConfig = new RuntimeConfigModel();
        return runtimeConfig;
    }

    ScriptConfig scriptConfig;
    MatchConfig matchConfig;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchConfig {
        Long matchmakerId;
        Long matchId;

        VersionModeModel modeConfig;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScriptConfig {
        Long scriptId;
    }
}
