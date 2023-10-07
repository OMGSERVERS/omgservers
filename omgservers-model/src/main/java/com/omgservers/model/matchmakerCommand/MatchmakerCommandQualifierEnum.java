package com.omgservers.model.matchmakerCommand;

import com.omgservers.model.matchmakerCommand.body.DeleteClientMatchmakerCommandBodyModel;

public enum MatchmakerCommandQualifierEnum {
    DELETE_CLIENT(DeleteClientMatchmakerCommandBodyModel.class);

    final Class<? extends MatchmakerCommandBodyModel> bodyClass;

    MatchmakerCommandQualifierEnum(Class<? extends MatchmakerCommandBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends MatchmakerCommandBodyModel> getBodyClass() {
        return bodyClass;
    }
}
