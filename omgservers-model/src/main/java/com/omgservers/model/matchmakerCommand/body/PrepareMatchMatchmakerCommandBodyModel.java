package com.omgservers.model.matchmakerCommand.body;

import com.omgservers.model.matchmakerCommand.MatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PrepareMatchMatchmakerCommandBodyModel extends MatchmakerCommandBodyModel {

    @NotNull
    Long matchId;

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.PREPARE_MATCH;
    }
}
