package com.omgservers.model.runtimeCommand;

import com.omgservers.model.runtimeCommand.body.AddPlayerRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.DeletePlayerRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.HandleEventRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.InitRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.StopRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.UpdateRuntimeCommandBodyModel;

public enum RuntimeCommandQualifierEnum {
    INIT_RUNTIME(InitRuntimeCommandBodyModel.class),
    STOP_RUNTIME(StopRuntimeCommandBodyModel.class),
    UPDATE_RUNTIME(UpdateRuntimeCommandBodyModel.class),
    ADD_PLAYER(AddPlayerRuntimeCommandBodyModel.class),
    DELETE_PLAYER(DeletePlayerRuntimeCommandBodyModel.class),
    HANDLE_EVENT(HandleEventRuntimeCommandBodyModel.class);

    Class<? extends RuntimeCommandBodyModel> bodyClass;

    RuntimeCommandQualifierEnum(Class<? extends RuntimeCommandBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends RuntimeCommandBodyModel> getBodyClass() {
        return bodyClass;
    }
}
