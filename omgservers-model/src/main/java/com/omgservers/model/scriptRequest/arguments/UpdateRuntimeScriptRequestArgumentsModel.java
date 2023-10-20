package com.omgservers.model.scriptRequest.arguments;

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
public class UpdateRuntimeScriptRequestArgumentsModel extends ScriptRequestArgumentsModel {

    @NotNull
    Long time;

    @Override
    public ScriptRequestQualifierEnum getQualifier() {
        return ScriptRequestQualifierEnum.UPDATE_RUNTIME;
    }
}
