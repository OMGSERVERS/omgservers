package com.omgservers.model.matchmakerCommand.body;

import com.omgservers.model.matchmakerCommand.MatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeleteClientMatchmakerCommandBodyModel extends MatchmakerCommandBodyModel {

    Long id;

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.DELETE_CLIENT;
    }
}
