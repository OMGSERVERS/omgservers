package com.omgservers.model.scriptEvent;

import com.omgservers.model.scriptEvent.body.AddClientEventBodyModel;
import com.omgservers.model.scriptEvent.body.DeleteClientScriptEventBodyModel;
import com.omgservers.model.scriptEvent.body.HandleMessageScriptEventBodyModel;
import com.omgservers.model.scriptEvent.body.InitRuntimeScriptEventBodyModel;
import com.omgservers.model.scriptEvent.body.SignedInScriptEventBodyModel;
import com.omgservers.model.scriptEvent.body.SignedUpScriptEventBodyModel;
import com.omgservers.model.scriptEvent.body.StopRuntimeScriptEventBodyModel;
import com.omgservers.model.scriptEvent.body.UpdateRuntimeScriptEventBodyModel;

public enum ScriptEventQualifierEnum {
    SIGNED_IN(SignedInScriptEventBodyModel.class),
    SIGNED_UP(SignedUpScriptEventBodyModel.class),

    INIT_RUNTIME(InitRuntimeScriptEventBodyModel.class),
    UPDATE_RUNTIME(UpdateRuntimeScriptEventBodyModel.class),
    STOP_RUNTIME(StopRuntimeScriptEventBodyModel.class),
    ADD_CLIENT(AddClientEventBodyModel.class),
    DELETE_CLIENT(DeleteClientScriptEventBodyModel.class),
    HANDLE_MESSAGE(HandleMessageScriptEventBodyModel.class);

    Class<? extends ScriptEventBodyModel> bodyClass;

    ScriptEventQualifierEnum(Class<? extends ScriptEventBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends ScriptEventBodyModel> getBodyClass() {
        return bodyClass;
    }
}
