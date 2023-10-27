package com.omgservers.model.matchCommand;

import com.omgservers.model.matchCommand.body.AddClientMatchCommandBodyModel;
import com.omgservers.model.matchCommand.body.DeleteClientMatchCommandBodyModel;

public enum MatchCommandQualifierEnum {
    ADD_CLIENT(AddClientMatchCommandBodyModel.class),
    DELETE_CLIENT(DeleteClientMatchCommandBodyModel.class);

    final Class<? extends MatchCommandBodyModel> bodyClass;

    MatchCommandQualifierEnum(Class<? extends MatchCommandBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends MatchCommandBodyModel> getBodyClass() {
        return bodyClass;
    }
}
