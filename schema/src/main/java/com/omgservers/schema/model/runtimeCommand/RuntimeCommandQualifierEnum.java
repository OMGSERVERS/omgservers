package com.omgservers.schema.model.runtimeCommand;

import com.omgservers.schema.model.runtimeCommand.body.AddClientRuntimeCommandBodyModel;
import com.omgservers.schema.model.runtimeCommand.body.AddMatchClientRuntimeCommandBodyModel;
import com.omgservers.schema.model.runtimeCommand.body.DeleteClientRuntimeCommandBodyModel;
import com.omgservers.schema.model.runtimeCommand.body.HandleMessageRuntimeCommandBodyModel;
import com.omgservers.schema.model.runtimeCommand.body.InitRuntimeCommandBodyModel;
import com.omgservers.schema.model.runtimeCommand.body.UpdateRuntimeCommandBodyModel;
import com.omgservers.schema.model.runtimeCommand.body.AddClientRuntimeCommandBodyModel;
import com.omgservers.schema.model.runtimeCommand.body.AddMatchClientRuntimeCommandBodyModel;
import com.omgservers.schema.model.runtimeCommand.body.DeleteClientRuntimeCommandBodyModel;
import com.omgservers.schema.model.runtimeCommand.body.HandleMessageRuntimeCommandBodyModel;
import com.omgservers.schema.model.runtimeCommand.body.InitRuntimeCommandBodyModel;
import com.omgservers.schema.model.runtimeCommand.body.UpdateRuntimeCommandBodyModel;
import lombok.Getter;

@Getter
public enum RuntimeCommandQualifierEnum {
    INIT_RUNTIME(InitRuntimeCommandBodyModel.class),
    UPDATE_RUNTIME(UpdateRuntimeCommandBodyModel.class),
    ADD_CLIENT(AddClientRuntimeCommandBodyModel.class),
    ADD_MATCH_CLIENT(AddMatchClientRuntimeCommandBodyModel.class),
    DELETE_CLIENT(DeleteClientRuntimeCommandBodyModel.class),
    HANDLE_MESSAGE(HandleMessageRuntimeCommandBodyModel.class);

    Class<? extends RuntimeCommandBodyModel> bodyClass;

    RuntimeCommandQualifierEnum(Class<? extends RuntimeCommandBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }
}
