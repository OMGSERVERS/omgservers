package com.omgservers.model.matchmakerCommand;

import com.omgservers.model.matchmakerCommand.body.AddRequestMatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerCommand.body.DeleteActorMatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerCommand.body.DeleteClientMatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerCommand.body.DeleteRequestMatchmakerCommandBodyModel;

public enum MatchmakerCommandQualifierEnum {
    ADD_REQUEST(AddRequestMatchmakerCommandBodyModel.class),
    DELETE_REQUEST(DeleteRequestMatchmakerCommandBodyModel.class),
    DELETE_ACTOR(DeleteActorMatchmakerCommandBodyModel.class),
    DELETE_CLIENT(DeleteClientMatchmakerCommandBodyModel.class);

    Class<? extends MatchmakerCommandBodyModel> bodyClass;

    MatchmakerCommandQualifierEnum(Class<? extends MatchmakerCommandBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends MatchmakerCommandBodyModel> getBodyClass() {
        return bodyClass;
    }
}
