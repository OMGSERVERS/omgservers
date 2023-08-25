package com.omgservers.model.matchmakerCommand.body;

import com.omgservers.model.matchmakerCommand.MatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.model.request.RequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AddRequestMatchmakerCommandBodyModel extends MatchmakerCommandBodyModel {

    RequestModel request;

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.ADD_REQUEST;
    }
}
