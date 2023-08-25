package com.omgservers.model.runtimeCommand;

import com.omgservers.model.runtimeCommand.body.IncomingRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.JoinRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.LeaveRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.StartRuntimeCommandBodyModel;

public enum RuntimeCommandQualifierEnum {
    START(StartRuntimeCommandBodyModel.class),
    JOIN(JoinRuntimeCommandBodyModel.class),
    LEAVE(LeaveRuntimeCommandBodyModel.class),
    INCOMING(IncomingRuntimeCommandBodyModel.class);

    Class<? extends RuntimeCommandBodyModel> bodyClass;

    RuntimeCommandQualifierEnum(Class<? extends RuntimeCommandBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends RuntimeCommandBodyModel> getBodyClass() {
        return bodyClass;
    }
}
