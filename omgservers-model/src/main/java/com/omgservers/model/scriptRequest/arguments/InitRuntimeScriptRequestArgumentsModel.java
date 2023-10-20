package com.omgservers.model.scriptRequest.arguments;

import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.scriptRequest.ScriptRequestArgumentsModel;
import com.omgservers.model.scriptRequest.ScriptRequestQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InitRuntimeScriptRequestArgumentsModel extends ScriptRequestArgumentsModel {

    @NotNull
    RuntimeConfigModel config;

    @Override
    public ScriptRequestQualifierEnum getQualifier() {
        return ScriptRequestQualifierEnum.INIT_RUNTIME;
    }
}
