package com.omgservers.model.scriptRequest.arguments;

import com.omgservers.model.scriptRequest.ScriptRequestArgumentsModel;
import com.omgservers.model.scriptRequest.ScriptRequestQualifierEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StopRuntimeScriptRequestArgumentsModel extends ScriptRequestArgumentsModel {

    @Override
    public ScriptRequestQualifierEnum getQualifier() {
        return ScriptRequestQualifierEnum.STOP_RUNTIME;
    }
}
