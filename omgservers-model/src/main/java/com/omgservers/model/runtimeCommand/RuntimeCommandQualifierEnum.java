package com.omgservers.model.runtimeCommand;

import com.omgservers.model.runtimeCommand.body.AddClientRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.ChangePlayerRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.DeleteClientRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.HandleMessageRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.InitRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.SignInRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.SignUpRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.StopRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.UpdateRuntimeCommandBodyModel;

public enum RuntimeCommandQualifierEnum {
    SIGN_IN(SignInRuntimeCommandBodyModel.class),
    SIGN_UP(SignUpRuntimeCommandBodyModel.class),
    CHANGE_PLAYER(ChangePlayerRuntimeCommandBodyModel.class),

    INIT_RUNTIME(InitRuntimeCommandBodyModel.class),
    UPDATE_RUNTIME(UpdateRuntimeCommandBodyModel.class),
    STOP_RUNTIME(StopRuntimeCommandBodyModel.class),
    ADD_CLIENT(AddClientRuntimeCommandBodyModel.class),
    DELETE_CLIENT(DeleteClientRuntimeCommandBodyModel.class),
    HANDLE_MESSAGE(HandleMessageRuntimeCommandBodyModel.class);

    Class<? extends RuntimeCommandBodyModel> bodyClass;

    RuntimeCommandQualifierEnum(Class<? extends RuntimeCommandBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends RuntimeCommandBodyModel> getBodyClass() {
        return bodyClass;
    }
}
