package com.omgservers.schema.model.matchmakerCommand.body;

import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandBodyModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class KickClientMatchmakerCommandBodyModel extends MatchmakerCommandBodyModel {

    @NotNull
    Long clientId;

    @NotNull
    Long matchId;

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.KICK_CLIENT;
    }
}
