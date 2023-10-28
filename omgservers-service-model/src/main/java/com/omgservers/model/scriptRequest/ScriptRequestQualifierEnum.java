package com.omgservers.model.scriptRequest;

import com.omgservers.model.scriptRequest.arguments.AddClientRequestArgumentsModel;
import com.omgservers.model.scriptRequest.arguments.ChangePlayerScriptRequestArgumentsModel;
import com.omgservers.model.scriptRequest.arguments.DeleteClientScriptRequestArgumentsModel;
import com.omgservers.model.scriptRequest.arguments.HandleMessageScriptRequestArgumentsModel;
import com.omgservers.model.scriptRequest.arguments.InitRuntimeScriptRequestArgumentsModel;
import com.omgservers.model.scriptRequest.arguments.SignInScriptRequestArgumentsModel;
import com.omgservers.model.scriptRequest.arguments.SignUpScriptRequestArgumentsModel;
import com.omgservers.model.scriptRequest.arguments.UpdateRuntimeScriptRequestArgumentsModel;

public enum ScriptRequestQualifierEnum {
    SIGN_IN(SignInScriptRequestArgumentsModel.class),
    SIGN_UP(SignUpScriptRequestArgumentsModel.class),
    CHANGE_PLAYER(ChangePlayerScriptRequestArgumentsModel.class),

    INIT_RUNTIME(InitRuntimeScriptRequestArgumentsModel.class),
    UPDATE_RUNTIME(UpdateRuntimeScriptRequestArgumentsModel.class),
    ADD_CLIENT(AddClientRequestArgumentsModel.class),
    DELETE_CLIENT(DeleteClientScriptRequestArgumentsModel.class),
    HANDLE_MESSAGE(HandleMessageScriptRequestArgumentsModel.class);

    Class<? extends ScriptRequestArgumentsModel> argumentsClass;

    ScriptRequestQualifierEnum(Class<? extends ScriptRequestArgumentsModel> argumentsClass) {
        this.argumentsClass = argumentsClass;
    }

    public Class<? extends ScriptRequestArgumentsModel> getArgumentsClass() {
        return argumentsClass;
    }
}
