package com.omgservers.model.scriptEvent;

import com.omgservers.model.scriptEvent.body.AddPlayerEventBodyModel;
import com.omgservers.model.scriptEvent.body.DeletePlayerScriptEventBodyModel;
import com.omgservers.model.scriptEvent.body.HandleMessageScriptEventBodyModel;
import com.omgservers.model.scriptEvent.body.InitScriptEventBodyModel;
import com.omgservers.model.scriptEvent.body.SignedInScriptEventBodyModel;
import com.omgservers.model.scriptEvent.body.SignedUpScriptEventBodyModel;
import com.omgservers.model.scriptEvent.body.StopScriptEventBodyModel;
import com.omgservers.model.scriptEvent.body.UpdateScriptEventBodyModel;

public enum ScriptEventQualifierEnum {
    SIGNED_IN(SignedInScriptEventBodyModel.class),
    SIGNED_UP(SignedUpScriptEventBodyModel.class),

    INIT(InitScriptEventBodyModel.class),
    UPDATE(UpdateScriptEventBodyModel.class),
    STOP(StopScriptEventBodyModel.class),
    ADD_PLAYER(AddPlayerEventBodyModel.class),
    DELETE_PLAYER(DeletePlayerScriptEventBodyModel.class),
    HANDLE_MESSAGE(HandleMessageScriptEventBodyModel.class);

    Class<? extends ScriptEventBodyModel> bodyClass;

    ScriptEventQualifierEnum(Class<? extends ScriptEventBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends ScriptEventBodyModel> getBodyClass() {
        return bodyClass;
    }
}
