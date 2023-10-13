package com.omgservers.model.match;

import com.omgservers.model.version.VersionModeModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchConfigModel {

    @NotNull
    VersionModeModel modeConfig;
}
