package com.omgservers.application.module.runtimeModule.model.command;

import com.omgservers.application.module.runtimeModule.model.command.body.IncomingCommandBodyModel;
import com.omgservers.application.module.runtimeModule.model.command.body.JoinCommandBodyModel;
import com.omgservers.application.module.runtimeModule.model.command.body.StartCommandBodyModel;

public enum CommandQualifierEnum {
    START(StartCommandBodyModel.class),
    JOIN(JoinCommandBodyModel.class),
    INCOMING(IncomingCommandBodyModel.class);

    Class<? extends CommandBodyModel> bodyClass;

    CommandQualifierEnum(Class<? extends CommandBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends CommandBodyModel> getBodyClass() {
        return bodyClass;
    }
}
