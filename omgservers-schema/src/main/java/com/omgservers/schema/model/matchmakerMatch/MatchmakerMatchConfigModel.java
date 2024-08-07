package com.omgservers.schema.model.matchmakerMatch;

import com.omgservers.schema.model.version.VersionModeDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchmakerMatchConfigModel {

    @NotNull
    VersionModeDto modeConfig;
}
