package com.omgservers.model.runtimeCommand;

import com.omgservers.model.runtimeCommand.body.AddActorRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.DeleteActorRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.HandleIncomingRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.InitRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.StopRuntimeCommandBodyModel;

public enum RuntimeCommandQualifierEnum {
    INIT_RUNTIME(InitRuntimeCommandBodyModel.class),
    STOP_RUNTIME(StopRuntimeCommandBodyModel.class),
    ADD_ACTOR(AddActorRuntimeCommandBodyModel.class),
    DELETE_ACTOR(DeleteActorRuntimeCommandBodyModel.class),
    HANDLE_INCOMING(HandleIncomingRuntimeCommandBodyModel.class);

    Class<? extends RuntimeCommandBodyModel> bodyClass;

    RuntimeCommandQualifierEnum(Class<? extends RuntimeCommandBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends RuntimeCommandBodyModel> getBodyClass() {
        return bodyClass;
    }
}
