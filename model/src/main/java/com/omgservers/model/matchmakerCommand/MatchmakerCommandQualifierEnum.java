package com.omgservers.model.matchmakerCommand;

import com.omgservers.model.matchmakerCommand.body.DeleteClientMatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerCommand.body.ExcludeMatchMatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerCommand.body.KickClientMatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerCommand.body.PrepareMatchMatchmakerCommandBodyModel;

public enum MatchmakerCommandQualifierEnum {
    DELETE_CLIENT(DeleteClientMatchmakerCommandBodyModel.class),
    KICK_CLIENT(KickClientMatchmakerCommandBodyModel.class),
    PREPARE_MATCH(PrepareMatchMatchmakerCommandBodyModel.class),
    EXCLUDE_MATCH(ExcludeMatchMatchmakerCommandBodyModel.class);

    final Class<? extends MatchmakerCommandBodyModel> bodyClass;

    MatchmakerCommandQualifierEnum(Class<? extends MatchmakerCommandBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends MatchmakerCommandBodyModel> getBodyClass() {
        return bodyClass;
    }
}
