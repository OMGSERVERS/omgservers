package com.omgservers.model.matchmakerCommand;

import com.omgservers.model.matchmakerCommand.body.DeleteClientMatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerCommand.body.KickClientMatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerCommand.body.StopMatchmakingCommandBodyModel;

public enum MatchmakerCommandQualifierEnum {
    DELETE_CLIENT(DeleteClientMatchmakerCommandBodyModel.class),
    KICK_CLIENT(KickClientMatchmakerCommandBodyModel.class),
    STOP_MATCHMAKING(StopMatchmakingCommandBodyModel.class);

    final Class<? extends MatchmakerCommandBodyModel> bodyClass;

    MatchmakerCommandQualifierEnum(Class<? extends MatchmakerCommandBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends MatchmakerCommandBodyModel> getBodyClass() {
        return bodyClass;
    }
}
